package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.output.QualifiedRunner
import com.reizu.snaphs.api.dto.output.Standing
import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.entity.PlayoffRule
import com.reizu.snaphs.api.exception.LeagueHasEndedException
import com.reizu.snaphs.api.dto.input.LeaguePlayoffRule as LeaguePlayoffRuleInput
import com.reizu.snaphs.api.dto.output.PlayoffRule as PlayoffRuleOutput
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.PlayoffRuleSeekService
import com.nhaarman.mockitokotlin2.any
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
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PlayoffServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validTop: Int = 1
    private val invalidTop: Int = 0

    private val validEndedOn: LocalDateTime? = C.VALID_ENDED_ON
    private val invalidEndedOn: LocalDateTime? = C.INVALID_ENDED_ON

    private val validSearch: String = "leagueName:$validLeagueName"
    private val invalidSearch: String = "leagueName:$invalidLeagueName"

    @Mock
    private lateinit var validLeague: League

    @Mock
    private lateinit var invalidLeague: League

    private val validStanding: Standing = EC.VALID_STANDING

    private val validStandings: List<Standing> = listOf(validStanding)
    private val validQualifiedRunners: List<QualifiedRunner> = validStandings
    private val noStandings: List<Standing> = emptyList()
    private val noQualifiedRunners: List<QualifiedRunner> = noStandings

    private val validLeaguePlayoffRuleInput: LeaguePlayoffRuleInput = EC.VALID_LEAGUE_PLAYOFF_RULE_INPUT
    private val invalidLeaguePlayoffRuleInput: LeaguePlayoffRuleInput = EC.INVALID_LEAGUE_PLAYOFF_RULE_INPUT

    private val validPlayoffRule: PlayoffRule = EC.VALID_PLAYOFF_RULE

    private val validPlayoffRules: List<PlayoffRule> = listOf(validPlayoffRule)
    private val validPlayoffRuleOutputs: List<PlayoffRuleOutput> = validPlayoffRules.map { playoffRule -> playoffRule.output }

    private val noPlayoffRules: List<PlayoffRule> = emptyList()
    private val noPlayoffRuleOutputs: List<PlayoffRuleOutput> = emptyList()

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var playoffRuleSeekService: PlayoffRuleSeekService

    @Mock
    private lateinit var leagueService: LeagueService

    @InjectMocks
    private lateinit var playoffService: PlayoffService

    @BeforeAll
    fun setUp() {
        whenever(validLeague.playoffRules).thenReturn(validPlayoffRules)
        whenever(validLeague.endedOn).thenReturn(validEndedOn)

        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        doNothing().whenever(leagueService).validateLeagueChange(validEndedOn)
        whenever(playoffRuleSeekService.create(any())).thenReturn(validPlayoffRule)
        whenever(playoffRuleSeekService.findAllActive(search = validSearch)).thenReturn(validPlayoffRules)
        whenever(playoffRuleSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)).thenReturn(validPlayoffRules)
        doNothing().whenever(playoffRuleSeekService).deleteAll(validPlayoffRules)

        whenever(invalidLeague.playoffRules).thenReturn(noPlayoffRules)
        whenever(invalidLeague.endedOn).thenReturn(invalidEndedOn)

        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        doThrow(LeagueHasEndedException::class).whenever(leagueService).validateLeagueChange(invalidEndedOn)
        whenever(playoffRuleSeekService.findAllActive(search = invalidSearch)).thenReturn(noPlayoffRules)
        whenever(playoffRuleSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(noPlayoffRules)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid leaguePlayoffRuleInput - when addRules - then return valid playoffRules`() {
            val expectedPlayoffRules: List<PlayoffRule> = validPlayoffRules

            val actualPlayoffRules: List<PlayoffRule> = playoffService.addRules(validLeaguePlayoffRuleInput)

            actualPlayoffRules shouldEqual expectedPlayoffRules
        }

        @Test
        fun `given valid parameters - when matchQualifiedRunners - then return valid qualifiedRunners`() {
            val expectedQualifiedRunners: List<QualifiedRunner> = validQualifiedRunners

            val actualQualifiedRunners: List<QualifiedRunner> = playoffService.matchQualifiedRunners(validLeagueName, validSeason, validTierLevel, validTop, validStandings)

            actualQualifiedRunners shouldEqual expectedQualifiedRunners
        }

        @Test
        fun `given valid leaguePlayoffRuleInput - when create - then return valid playoffRuleOutputs`() {
            val expectedPlayoffRuleOutputs: List<PlayoffRuleOutput> = validPlayoffRuleOutputs

            val actualPlayoffRuleOutputs: Iterable<PlayoffRuleOutput> = playoffService.create(validLeaguePlayoffRuleInput)

            actualPlayoffRuleOutputs shouldEqual expectedPlayoffRuleOutputs
        }

        @Test
        fun `given valid search - when findAll - then return valid playoffRuleOutputs`() {
            val expectedPlayoffRuleOutputs: List<PlayoffRuleOutput> = validPlayoffRuleOutputs

            val actualPlayoffRuleOutputs: Iterable<PlayoffRuleOutput> = playoffService.findAll(validSearch)

            actualPlayoffRuleOutputs shouldEqual expectedPlayoffRuleOutputs
        }

        @Test
        fun `given valid league parameters - when findAll - then return valid playoffRuleOutputs`() {
            val expectedPlayoffRuleOutputs: List<PlayoffRuleOutput> = validPlayoffRuleOutputs

            val actualPlayoffRuleOutputs: Iterable<PlayoffRuleOutput> = playoffService.findAll(validLeagueName, validSeason, validTierLevel)

            actualPlayoffRuleOutputs shouldEqual expectedPlayoffRuleOutputs
        }

        @Test
        fun `given valid leaguePlayoffRuleInput - when replace - then return valid playoffRuleOutputs`() {
            val expectedPlayoffRuleOutputs: List<PlayoffRuleOutput> = validPlayoffRuleOutputs

            val actualPlayoffRuleOutputs: Iterable<PlayoffRuleOutput> = playoffService.replace(validLeaguePlayoffRuleInput)

            actualPlayoffRuleOutputs shouldEqual expectedPlayoffRuleOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given ended league(s) - when addRules - then throw LeagueHasEndedException`() {
            invoking { playoffService.addRules(invalidLeaguePlayoffRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid parameters - when matchQualifiedRunners - then return no qualifiedRunners`() {
            val expectedQualifiedRunners: List<QualifiedRunner> = noQualifiedRunners

            val actualQualifiedRunners: List<QualifiedRunner> = playoffService.matchQualifiedRunners(invalidLeagueName, invalidSeason, invalidTierLevel, invalidTop, noStandings)

            actualQualifiedRunners shouldEqual expectedQualifiedRunners
        }

        @Test
        fun `given ended league(s) - when create - then throw LeagueHasEndedException`() {
            invoking { playoffService.create(invalidLeaguePlayoffRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no playoffRuleOutputs`() {
            val expectedPlayoffRuleOutputs: List<PlayoffRuleOutput> = noPlayoffRuleOutputs

            val actualPlayoffRuleOutputs: Iterable<PlayoffRuleOutput> = playoffService.findAll(invalidSearch)

            actualPlayoffRuleOutputs shouldEqual expectedPlayoffRuleOutputs
        }

        @Test
        fun `given invalid league parameters - when findAll - then return no playoffRuleOutputs`() {
            val expectedPlayoffRuleOutputs: List<PlayoffRuleOutput> = noPlayoffRuleOutputs

            val actualPlayoffRuleOutputs: Iterable<PlayoffRuleOutput> = playoffService.findAll(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualPlayoffRuleOutputs shouldEqual expectedPlayoffRuleOutputs
        }

        @Test
        fun `given ended league(s) - when replace - then throw LeagueHasEndedException`() {
            invoking { playoffService.replace(invalidLeaguePlayoffRuleInput) } shouldThrow LeagueHasEndedException::class
        }

    }

}
