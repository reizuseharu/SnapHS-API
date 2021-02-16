package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.output.QualifiedRunner
import com.reizu.snaphs.api.dto.output.Standing
import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.exception.RaceUnfinishedException
import com.reizu.snaphs.api.dto.output.RaceTime as RaceTimeOutput
import com.reizu.snaphs.api.dto.output.Standing as StandingOutput
import com.reizu.snaphs.api.service.TestConstants as C
import com.reizu.snaphs.api.service.TestEntityConstants as EC
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.RaceRunnerSeekService
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

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StandingServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validDefaultPoints: Int = C.VALID_DEFAULT_POINTS

    private val validDefaultTime: Long = C.VALID_DEFAULT_TIME

    private val validRaceName: String = C.VALID_RACE_NAME
    private val invalidRaceName: String = C.INVALID_RACE_NAME

    private val validTop: Int = 1
    private val invalidTop: Int = 0

    @Mock
    private lateinit var validLeague: League

    @Mock
    private lateinit var invalidLeague: League

    private val validRace: Race = EC.VALID_RACE

    private val validRaces: Set<Race> = setOf(validRace)
    private val noRaces: Set<Race> = emptySet()

    private val validRaceRunner: RaceRunner = EC.VALID_RACE_RUNNER.copy(outcome = Outcome.COMPLETED)

    private val validRaceRunners: List<RaceRunner> = listOf(validRaceRunner)
    private val noRaceRunners: List<RaceRunner> = emptyList()

    private val validPointRule: PointRule = EC.VALID_POINT_RULE

    private val validPointRules: Set<PointRule> = setOf(validPointRule)
    private val noPointRules: Set<PointRule> = emptySet()

    private val validStanding: Standing = EC.VALID_STANDING

    private val validStandings: List<Standing> = listOf(validStanding)
    private val validQualifiedRunners: List<QualifiedRunner> = validStandings
    private val noStandings: List<Standing> = emptyList()
    private val noQualifiedRunners: List<QualifiedRunner> = noStandings

    private val validRaceTimeOutput: RaceTimeOutput = EC.VALID_RACE_RUNNER.output.copy(outcome = Outcome.COMPLETED)

    private val validRaceTimeOutputs: List<RaceTimeOutput> = listOf(validRaceTimeOutput)
    private val noRaceTimeOutputs: List<RaceTimeOutput> = emptyList()

    @Mock
    private lateinit var playoffService: PlayoffService

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @InjectMocks
    private lateinit var standingService: StandingService

    @BeforeAll
    fun setUp() {
        whenever(validLeague.races).thenReturn(validRaces)
        whenever(validLeague.pointRules).thenReturn(validPointRules)
        whenever(validLeague.defaultPoints).thenReturn(validDefaultPoints)
        whenever(validLeague.defaultTime).thenReturn(validDefaultTime)

        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        whenever(raceRunnerSeekService.findAllByRace(validRaceName)).thenReturn(validRaceRunners)
        whenever(playoffService.matchQualifiedRunners(validLeagueName, validSeason, validTierLevel, validTop, validStandings)).thenReturn(validStandings)

        whenever(invalidLeague.races).thenReturn(noRaces)
        whenever(invalidLeague.pointRules).thenReturn(noPointRules)

        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        whenever(raceRunnerSeekService.findAllByRace(invalidRaceName)).thenReturn(noRaceRunners)
        whenever(playoffService.matchQualifiedRunners(invalidLeagueName, invalidSeason, invalidTierLevel, invalidTop, noStandings)).thenReturn(noStandings)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid league parameters - when calculateStandings - then return valid standings`() {
            val expectedStandings: List<Standing> = validStandings

            val actualStandings: List<Standing> = standingService.calculateStandings(validLeagueName, validSeason, validTierLevel)

            actualStandings shouldEqual expectedStandings
        }

        @Test
        fun `given valid league parameters - when generateStandings - then return valid standingOutputs`() {
            val expectedStandings: List<StandingOutput> = validStandings

            val actualStandings: Iterable<StandingOutput> = standingService.generateStandings(validLeagueName, validSeason, validTierLevel)

            actualStandings shouldEqual expectedStandings
        }

        @Test
        fun `given valid league parameters - when findQualifiedRunners - then return valid qualifiedRunners`() {
            val expectedQualifiedRunners: List<QualifiedRunner> = validQualifiedRunners

            val actualQualifiedRunners: Iterable<QualifiedRunner> = standingService.findQualifiedRunners(validLeagueName, validSeason, validTierLevel, validTop)

            actualQualifiedRunners shouldEqual expectedQualifiedRunners
        }

        @Test
        fun `given valid race name - when calculatePlacements - then return valid raceTimeOutputs`() {
            val expectedRaceTimeOutputs: List<RaceTimeOutput> = validRaceTimeOutputs

            val actualRaceTimeOutputs: List<RaceTimeOutput> = standingService.calculatePlacements(validRaceName)

            actualRaceTimeOutputs shouldEqual expectedRaceTimeOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid league parameters - when calculateStandings - then return no standings`() {
            val expectedStandings: List<Standing> = noStandings

            val actualStandings: List<Standing> = standingService.calculateStandings(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualStandings shouldEqual expectedStandings
        }

        @Test
        fun `given invalid league parameters - when generateStandings - then return no standingOutputs`() {
            val expectedStandings: List<StandingOutput> = noStandings

            val actualStandings: Iterable<StandingOutput> = standingService.generateStandings(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualStandings shouldEqual expectedStandings
        }

        @Test
        fun `given invalid league parameters - when findQualifiedRunners - then return no qualifiedRunners`() {
            val expectedQualifiedRunners: List<QualifiedRunner> = noQualifiedRunners

            val actualQualifiedRunners: Iterable<QualifiedRunner> = standingService.findQualifiedRunners(invalidLeagueName, invalidSeason, invalidTierLevel, invalidTop)

            actualQualifiedRunners shouldEqual expectedQualifiedRunners
        }

        @Test
        fun `given invalid race name - when calculatePlacements - then return no raceTimeOutputs`() {
            whenever(raceRunnerSeekService.findAllByRace(invalidRaceName)).thenReturn(noRaceRunners)

            val expectedRaceTimeOutputs: List<RaceTimeOutput> = noRaceTimeOutputs

            val actualRaceTimeOutputs: List<RaceTimeOutput> = standingService.calculatePlacements(invalidRaceName)

            actualRaceTimeOutputs shouldEqual expectedRaceTimeOutputs
        }

        @Test
        fun `given invalid outcome - when calculatePlacements - then throw RaceUnfinishedException`() {
            val invalidRaceRunner: RaceRunner = EC.INVALID_RACE_RUNNER.copy(outcome = Outcome.PENDING_VERIFICATION)
            val invalidRaceRunners: List<RaceRunner> = listOf(invalidRaceRunner)
            whenever(raceRunnerSeekService.findAllByRace(invalidRaceName)).thenReturn(invalidRaceRunners)

            invoking { standingService.calculatePlacements(invalidRaceName) } shouldThrow RaceUnfinishedException::class
        }

        @Test
        fun `given invalid time - when calculatePlacements - then throw RaceUnfinishedException`() {
            val invalidRaceRunner: RaceRunner = EC.INVALID_RACE_RUNNER.copy(outcome = Outcome.COMPLETED, time = null)
            val invalidRaceRunners: List<RaceRunner> = listOf(invalidRaceRunner)
            whenever(raceRunnerSeekService.findAllByRace(invalidRaceName)).thenReturn(invalidRaceRunners)

            invoking { standingService.calculatePlacements(invalidRaceName) } shouldThrow RaceUnfinishedException::class
        }

    }

}
