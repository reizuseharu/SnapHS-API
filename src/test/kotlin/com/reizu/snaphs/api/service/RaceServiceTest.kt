package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.dto.input.Race as RaceInput
import com.reizu.snaphs.api.dto.output.Race as RaceOutput
import com.reizu.snaphs.api.entity.Race
import com.reizu.snaphs.api.entity.RaceRunner
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.RaceRunnerSeekService
import com.reizu.snaphs.api.service.seek.RaceSeekService
import com.reizu.snaphs.api.service.TestConstants as C
import com.reizu.snaphs.api.service.TestEntityConstants as EC
import com.nhaarman.mockitokotlin2.doNothing
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
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaceServiceTest {

    private val validName: String = C.VALID_RACE_NAME
    private val invalidName: String = C.INVALID_RACE_NAME

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validRunnerName: String = C.VALID_RUNNER_NAME
    private val invalidRunnerName: String = C.INVALID_RUNNER_NAME

    private val validSearch: String? = "name:$validName"
    private val invalidSearch: String? = "name:$invalidName"

    private val validRaceInput: RaceInput = EC.VALID_RACE_INPUT
    private val invalidRaceInput: RaceInput = EC.INVALID_RACE_INPUT

    private val validLeague: League = EC.VALID_LEAGUE
    private val invalidLeague: League = EC.INVALID_LEAGUE

    private val validRace: Race = EC.VALID_RACE
    private val invalidRace: Race = EC.INVALID_RACE

    private val validRaces: List<Race> = listOf(validRace)
    private val validRaceOutputs: List<RaceOutput> = validRaces.map { race -> race.output }

    private val noRaces: List<Race> = emptyList()
    private val noRaceOutputs: List<RaceOutput> = emptyList()

    private val validRaceRunner: RaceRunner = EC.VALID_RACE_RUNNER

    private val validRaceRunners: List<RaceRunner> = listOf(validRaceRunner)
    private val noRaceRunners: List<RaceRunner> = emptyList()

    @Mock
    private lateinit var raceSeekService: RaceSeekService

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @InjectMocks
    private lateinit var raceService: RaceService

    @Mock
    private lateinit var leagueService: LeagueService

    @BeforeAll
    fun setUp() {
        whenever(raceSeekService.findAllActive(search = validSearch)).thenReturn(validRaces)
        whenever(raceRunnerSeekService.findAllByRunner(validRunnerName)).thenReturn(validRaceRunners)
        whenever(raceSeekService.create(validRace)).thenReturn(validRace)
        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)

        doNothing().whenever(leagueService).validateLeagueChange(Mockito.any(LocalDateTime::class.java), Mockito.anyString())
        // ! Add failure case for validateLeagueChange

        whenever(raceSeekService.findAllActive(search = invalidSearch)).thenReturn(noRaces)
        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        whenever(raceRunnerSeekService.findAllByRunner(invalidRunnerName)).thenReturn(noRaceRunners)
        doThrow(IllegalArgumentException::class).whenever(raceSeekService).create(invalidRace)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid raceInput - when create - then return valid raceOutput`() {
            val expectedRaceOutput: RaceOutput = validRace.output

            val actualRaceOutput: RaceOutput = raceService.create(validRaceInput)

            actualRaceOutput shouldEqual expectedRaceOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid raceOutputs`() {
            val expectedRaceOutputs: Iterable<RaceOutput> = validRaceOutputs

            val actualRaceOutputs: Iterable<RaceOutput> = raceService.findAll(validSearch)

            actualRaceOutputs shouldEqual expectedRaceOutputs
        }

        @Test
        fun `given valid runner name - when findAllRaces - then return valid raceOutputs`() {
            val expectedRaceOutputs: Iterable<RaceOutput> = validRaceOutputs

            val actualRaceOutputs: Iterable<RaceOutput> = raceService.findAllRaces(validRunnerName)

            actualRaceOutputs shouldEqual expectedRaceOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid raceInput - when create - then throw IllegalArgumentException`() {
            invoking { raceService.create(invalidRaceInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no raceOutputs`() {
            val expectedRaceOutputs: Iterable<RaceOutput> = noRaceOutputs

            val actualRaceOutputs: Iterable<RaceOutput> = raceService.findAll(invalidSearch)

            actualRaceOutputs shouldEqual expectedRaceOutputs
        }

        @Test
        fun `given invalid runner name - when findAllRaces - then return no raceOutputs`() {
            val expectedRaceOutputs: Iterable<RaceOutput> = noRaceOutputs

            val actualRaceOutputs: Iterable<RaceOutput> = raceService.findAllRaces(invalidRunnerName)

            actualRaceOutputs shouldEqual expectedRaceOutputs
        }

    }

}
