package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.exception.NotRegisteredException
import com.reizu.snaphs.api.service.seek.LeagueRunnerSeekService
import com.reizu.snaphs.api.dto.input.RaceTime as RaceTimeInput
import com.reizu.snaphs.api.dto.output.RaceTime as RaceTimeOutput
import com.reizu.snaphs.api.dto.update.RaceTime as RaceTimeRegister
import com.reizu.snaphs.api.service.seek.RaceRunnerSeekService
import com.reizu.snaphs.api.service.seek.RaceSeekService
import com.reizu.snaphs.api.service.seek.RunnerSeekService
import com.reizu.snaphs.api.service.TestConstants as C
import com.reizu.snaphs.api.service.TestEntityConstants as EC
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaceTimeServiceTest {

    private val validRaceName: String = C.VALID_RACE_NAME
    private val invalidRaceName: String = C.INVALID_RACE_NAME

    private val validRunnerName: String = C.VALID_RUNNER_NAME
    private val invalidRunnerName: String = C.INVALID_RUNNER_NAME

    private val validRaceTimeInput: RaceTimeInput = EC.VALID_RACE_TIME_INPUT
    private val invalidRaceTimeInput: RaceTimeInput = EC.INVALID_RACE_TIME_INPUT

    private val validRaceTimeRegister: RaceTimeRegister = EC.VALID_RACE_TIME_REGISTER
    private val invalidRaceTimeRegister: RaceTimeRegister = EC.INVALID_RACE_TIME_REGISTER

    private val validRace: Race = EC.VALID_RACE
    private val invalidRace: Race = EC.INVALID_RACE

    private val validRunner: Runner = EC.VALID_RUNNER
    private val invalidRunner: Runner = EC.INVALID_RUNNER

    private val validLeagueRunner: LeagueRunner = EC.VALID_LEAGUE_RUNNER
    private val invalidLeagueRunner: LeagueRunner = EC.INVALID_LEAGUE_RUNNER

    private val validLeagueRunners: List<LeagueRunner> = listOf(validLeagueRunner)
    private val noLeagueRunners: List<LeagueRunner> = emptyList()

    private val validRaceRunner: RaceRunner = EC.VALID_RACE_RUNNER
    private val invalidRaceRunner: RaceRunner = EC.INVALID_RACE_RUNNER

    @Mock
    private lateinit var raceSeekService: RaceSeekService

    @Mock
    private lateinit var runnerSeekService: RunnerSeekService

    @Mock
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @InjectMocks
    private lateinit var raceTimeService: RaceTimeService

    @BeforeAll
    fun setUp() {
        whenever(raceSeekService.findByName(validRaceName)).thenReturn(validRace)
        whenever(runnerSeekService.findByName(validRunnerName)).thenReturn(validRunner)
        whenever(leagueRunnerSeekService.findAllByRunner(validRunnerName)).thenReturn(validLeagueRunners)
        whenever(raceRunnerSeekService.create(validRaceRunner)).thenReturn(validRaceRunner)
        whenever(raceRunnerSeekService.registerRaceTime(validRaceTimeRegister)).thenReturn(validRaceRunner)
        
        whenever(raceSeekService.findByName(invalidRaceName)).thenReturn(invalidRace)
        whenever(runnerSeekService.findByName(invalidRunnerName)).thenReturn(invalidRunner)
        doThrow(IllegalArgumentException::class).whenever(raceRunnerSeekService).create(invalidRaceRunner)
        doThrow(EntityNotFoundException::class).whenever(raceRunnerSeekService).registerRaceTime(invalidRaceTimeRegister)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid raceTimeInput - when register - then return valid raceTimeOutput`() {
            val expectedRaceTimeOutput: RaceTimeOutput = validRaceRunner.output

            val actualRaceTimeOutput: RaceTimeOutput = raceTimeService.register(validRaceTimeInput)

            actualRaceTimeOutput shouldEqual expectedRaceTimeOutput
        }

        @Test
        fun `given valid raceTimeRegister - when registerTime - then return valid raceTimeOutput`() {
            val expectedRaceTimeOutput: RaceTimeOutput = validRaceRunner.output

            val actualRaceTimeOutput: RaceTimeOutput = raceTimeService.registerTime(validRaceTimeRegister)

            actualRaceTimeOutput shouldEqual expectedRaceTimeOutput
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid raceTimeInput - when register - then throw IllegalArgumentException`() {
            val invalidLeagueRunners: List<LeagueRunner> = listOf(invalidLeagueRunner)
            whenever(leagueRunnerSeekService.findAllByRunner(invalidRunnerName)).thenReturn(invalidLeagueRunners)

            invoking { raceTimeService.register(invalidRaceTimeInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given unregistered runner - when register - then throw NotRegisteredException`() {
            whenever(leagueRunnerSeekService.findAllByRunner(invalidRunnerName)).thenReturn(noLeagueRunners)

            invoking { raceTimeService.register(invalidRaceTimeInput) } shouldThrow NotRegisteredException::class
        }

        @Test
        fun `given invalid raceTimeRegister - when registerTime - then throw EntityNotFoundException`() {
            invoking { raceTimeService.registerTime(invalidRaceTimeRegister) } shouldThrow EntityNotFoundException::class
        }

    }

}
