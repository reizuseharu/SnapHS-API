package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.exception.LeagueIsFullException
import com.reizu.snaphs.api.service.seek.LeagueRunnerSeekService
import com.reizu.snaphs.api.dto.input.LeagueRunner as LeagueRunnerInput
import com.reizu.snaphs.api.dto.output.LeagueRunner as LeagueRunnerOutput
import com.reizu.snaphs.api.dto.input.Runner as RunnerInput
import com.reizu.snaphs.api.dto.output.Runner as RunnerOutput
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.RaceRunnerSeekService
import com.reizu.snaphs.api.service.seek.RunnerSeekService
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
internal class RunnerServiceTest {

    private val validName: String = C.VALID_RUNNER_NAME
    private val invalidName: String = C.INVALID_RUNNER_NAME

    private val validRaceName: String = C.VALID_RACE_NAME
    private val invalidRaceName: String = C.INVALID_RACE_NAME

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL
    
    private val validRunnerLimit: Int = C.VALID_RUNNER_LIMIT
    private val invalidRunnerLimit: Int = C.INVALID_RUNNER_LIMIT

    private val validSearch: String? = "name:$validName"
    private val invalidSearch: String? = "name:$invalidName"

    private val validRunnerInput: RunnerInput = EC.VALID_RUNNER_INPUT
    private val invalidRunnerInput: RunnerInput = EC.INVALID_RUNNER_INPUT
    
    private val validLeagueRunnerInput: LeagueRunnerInput = EC.VALID_LEAGUE_RUNNER_INPUT
    private val invalidLeagueRunnerInput: LeagueRunnerInput = EC.INVALID_LEAGUE_RUNNER_INPUT

    private val validLeague: League = EC.VALID_LEAGUE
    private val invalidLeague: League = EC.INVALID_LEAGUE

    private val validRunner: Runner = EC.VALID_RUNNER
    private val invalidRunner: Runner = EC.INVALID_RUNNER

    private val validRunners: List<Runner> = listOf(validRunner)
    private val validRunnerOutputs: List<RunnerOutput> = validRunners.map { race -> race.output }

    private val noRunners: List<Runner> = emptyList()
    private val noRunnerOutputs: List<RunnerOutput> = emptyList()

    private val validRaceRunner: RaceRunner = EC.VALID_RACE_RUNNER

    private val validRaceRunners: List<RaceRunner> = listOf(validRaceRunner)
    private val noRaceRunners: List<RaceRunner> = emptyList()

    private val validLeagueRunner: LeagueRunner = EC.VALID_LEAGUE_RUNNER
    private val invalidLeagueRunner: LeagueRunner = EC.INVALID_LEAGUE_RUNNER
    
    private val validLeagueRunners: List<LeagueRunner> = listOf(validLeagueRunner)
    private val noLeagueRunners: List<LeagueRunner> = emptyList()

    @Mock
    private lateinit var runnerSeekService: RunnerSeekService

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @Mock
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @InjectMocks
    private lateinit var runnerService: RunnerService

    @Mock
    private lateinit var leagueService: LeagueService

    @BeforeAll
    fun setUp() {
        whenever(runnerSeekService.findAllActive(search = validSearch)).thenReturn(validRunners)
        whenever(runnerSeekService.findByName(validName)).thenReturn(validRunner)
        whenever(raceRunnerSeekService.findAllByRace(validRaceName)).thenReturn(validRaceRunners)
        whenever(runnerSeekService.create(validRunner)).thenReturn(validRunner)
        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        whenever(leagueSeekService.findBottomLeague(validLeagueName, validSeason)).thenReturn(validLeague)
        whenever(leagueRunnerSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeagueRunners)
        whenever(leagueRunnerSeekService.create(validLeagueRunner)).thenReturn(validLeagueRunner)

        doNothing().whenever(leagueService).validateLeagueChange(Mockito.any(LocalDateTime::class.java), Mockito.anyString())
        // ! Add failure case for validateLeagueChange

        whenever(runnerSeekService.findAllActive(search = invalidSearch)).thenReturn(noRunners)
        whenever(runnerSeekService.findByName(invalidName)).thenReturn(invalidRunner)
        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        whenever(raceRunnerSeekService.findAllByRace(invalidRaceName)).thenReturn(noRaceRunners)
        whenever(leagueRunnerSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(noLeagueRunners)
        doThrow(IllegalArgumentException::class).whenever(runnerSeekService).create(invalidRunner)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid runnerInput - when create - then return valid runnerOutput`() {
            val expectedRunnerOutput: RunnerOutput = validRunner.output

            val actualRunnerOutput: RunnerOutput = runnerService.create(validRunnerInput)

            actualRunnerOutput shouldEqual expectedRunnerOutput
        }

        @Test
        fun `given valid leagueRunnerInput - when register - then return valid leagueRunnerOutput`() {
            val expectedLeagueRunnerOutput: LeagueRunnerOutput = validLeagueRunner.output

            val actualLeagueRunnerOutput: LeagueRunnerOutput = runnerService.register(validLeagueRunnerInput)

            actualLeagueRunnerOutput shouldEqual expectedLeagueRunnerOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = validRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAll(validSearch)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

        @Test
        fun `given valid race name - when findAllRunners - then return valid runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = validRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAllRunners(validRaceName)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

        @Test
        fun `given valid parameters - when findAllRunners - then return valid runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = validRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAllRunners(validLeagueName, validSeason, validTierLevel)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid runnerInput - when create - then throw IllegalArgumentException`() {
            invoking { runnerService.create(invalidRunnerInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid runnerLimit - when register - then throw LeagueIsFullException`() {
            whenever(leagueSeekService.findBottomLeague(invalidLeagueName, invalidSeason)).thenReturn(invalidLeague)

            invoking { runnerService.register(invalidLeagueRunnerInput) } shouldThrow LeagueIsFullException::class
        }

        @Test
        fun `given invalid leagueRunnerInput - when register - then throw IllegalArgumentException`() {
            val invalidLeague: League = invalidLeague.copy(runnerLimit = validRunnerLimit)
            whenever(leagueSeekService.findBottomLeague(invalidLeagueName, invalidSeason)).thenReturn(invalidLeague)

            val invalidLeagueRunner: LeagueRunner = invalidLeagueRunner.copy(league = invalidLeague)
            doThrow(IllegalArgumentException::class).whenever(leagueRunnerSeekService).create(invalidLeagueRunner)

            invoking { runnerService.register(invalidLeagueRunnerInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = noRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAll(invalidSearch)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

        @Test
        fun `given invalid race name - when findAllRunners - then return no runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = noRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAllRunners(invalidRaceName)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

        @Test
        fun `given invalid parameters - when findAllRunners - then return no runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = noRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAllRunners(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

    }

}
