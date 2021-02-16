package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.output.Match
import com.reizu.snaphs.api.dto.search.League as LeagueSearch
import com.reizu.snaphs.api.dto.output.QualifiedRunner
import com.reizu.snaphs.api.dto.output.Standing
import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.exception.LeagueHasEndedException
import com.reizu.snaphs.api.service.seek.*
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import com.reizu.snaphs.api.service.TestConstants as C
import com.reizu.snaphs.api.service.TestEntityConstants as EC
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
internal class SeasonServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validRunnerName: String = C.VALID_RUNNER_NAME
    
    private val validLeagueSearch: LeagueSearch = LeagueSearch(
        name = validLeagueName,
        season = validSeason,
        tierLevel = validTierLevel
    )

    private val invalidLeagueSearch: LeagueSearch = LeagueSearch(
        name = invalidLeagueName,
        season = invalidSeason,
        tierLevel = invalidTierLevel
    )

    private val validRunner: Runner = EC.VALID_RUNNER

    private val validLeague: League = EC.VALID_LEAGUE
    private val invalidLeague: League = EC.INVALID_LEAGUE

    private val validRace: Race = EC.VALID_RACE

    private val validRaceRunner: RaceRunner = EC.VALID_RACE_RUNNER

    private val validLeagues: List<League> = listOf(validLeague)
    private val invalidLeagues: List<League> = listOf(invalidLeague)

    private val validStanding: Standing = EC.VALID_STANDING

    private val validStandings: List<Standing> = listOf(validStanding)

    private val validQualifiedRunners: List<QualifiedRunner> = validStandings

    private val validLeagueRunner: LeagueRunner = EC.VALID_LEAGUE_RUNNER

    private val validLeagueRunners: List<LeagueRunner> = listOf(validLeagueRunner)

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @Mock
    private lateinit var runnerSeekService: RunnerSeekService

    @Mock
    private lateinit var raceSeekService: RaceSeekService

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @Mock
    private lateinit var leagueService: LeagueService

    @Mock
    private lateinit var standingService: StandingService

    @Mock
    private lateinit var divisionShiftService: DivisionShiftService

    @InjectMocks
    private lateinit var seasonService: SeasonService

    @BeforeAll
    fun setUp() {
        doNothing().whenever(leagueService).validateLeagueChange(validLeague.endedOn)
        whenever(standingService.calculateStandings(validLeagueName, validSeason, validTierLevel)).thenReturn(validStandings)
        whenever(divisionShiftService.matchQualifiedRunners(validLeagueName, validSeason, validTierLevel, validStandings, Shift.RELEGATION)).thenReturn(validQualifiedRunners)
        whenever(divisionShiftService.matchQualifiedRunners(validLeagueName, validSeason, validTierLevel, validStandings, Shift.PROMOTION)).thenReturn(validQualifiedRunners)
        whenever(leagueSeekService.find(validLeagueName, validSeason + 1, validTierLevel + 1)).thenReturn(validLeague)
        whenever(leagueSeekService.find(validLeagueName, validSeason + 1, validTierLevel - 1)).thenReturn(validLeague)
        whenever(runnerSeekService.findByName(validRunnerName)).thenReturn(validRunner)
        whenever(leagueRunnerSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeagueRunners)
        whenever(leagueSeekService.find(validLeagueName, validSeason + 1, validTierLevel)).thenReturn(validLeague)
        whenever(leagueRunnerSeekService.create(validLeagueRunner)).thenReturn(validLeagueRunner)
        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        whenever(raceSeekService.create(validRace)).thenReturn(validRace)
        whenever(raceRunnerSeekService.create(validRaceRunner)).thenReturn(validRaceRunner)

        doThrow(LeagueHasEndedException::class).whenever(leagueService).validateLeagueChange(invalidLeague.endedOn)
        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid leagues - when shiftDivisions - then succeed`() {
            seasonService.shiftDivisions(validLeagues)
        }

        @Test
        fun `given valid league parameters - when generateRoundRobin - then return valid matches`() {
            // ! Need to add more than one league and runner for testing
            val expectedMatches: List<Match> = emptyList()

            val actualMatches: List<Match> = seasonService.generateRoundRobin(validLeagueSearch)

            actualMatches shouldEqual expectedMatches
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given ended league(s) - when shiftDivisions - then throw LeagueHasEndedException`() {
            invoking { seasonService.shiftDivisions(invalidLeagues) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given ended league(s) - when generateRoundRobin - then throw LeagueHasEndedException`() {
            invoking { seasonService.generateRoundRobin(invalidLeagueSearch) } shouldThrow LeagueHasEndedException::class
        }

    }
}
