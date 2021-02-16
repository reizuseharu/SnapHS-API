package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.input.EndSeason
import com.reizu.snaphs.api.dto.input.LeagueDivisionShiftRule
import com.reizu.snaphs.api.dto.input.LowerTier
import com.reizu.snaphs.api.dto.input.StartSeason
import com.reizu.snaphs.api.dto.output.LeagueSchedule
import com.reizu.snaphs.api.dto.output.Match
import com.reizu.snaphs.api.entity.DivisionShiftRule
import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.entity.LeagueSpeedrun
import com.reizu.snaphs.api.entity.PlayoffRule
import com.reizu.snaphs.api.entity.PointRule
import com.reizu.snaphs.api.exception.LeagueHasEndedException
import com.reizu.snaphs.api.dto.input.League as LeagueInput
import com.reizu.snaphs.api.dto.input.LeaguePlayoffRule as LeaguePlayoffRuleInput
import com.reizu.snaphs.api.dto.input.LeaguePointRule as LeaguePointRuleInput
import com.reizu.snaphs.api.dto.output.League as LeagueOutput
import com.reizu.snaphs.api.dto.output.PlayoffRule as PlayoffRuleOutput
import com.reizu.snaphs.api.dto.output.PointRule as PointRuleOutput
import com.reizu.snaphs.api.dto.search.League as LeagueSearch
import com.reizu.snaphs.api.dto.update.LeagueDivisionShift as LeagueDivisionShiftUpdate
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.LeagueSpeedrunSeekService
import com.reizu.snaphs.api.service.TestConstants as C
import com.reizu.snaphs.api.service.TestEntityConstants as EC
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.any
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
internal class LeagueServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validParentTierLevel: Int = C.VALID_PARENT_TIER_LEVEL
    private val invalidParentTierLevel: Int = C.INVALID_PARENT_TIER_LEVEL

    private val validChildTierLevel: Int = C.VALID_CHILD_TIER_LEVEL
    private val invalidChildTierLevel: Int = C.INVALID_CHILD_TIER_LEVEL

    private val validSearch: String = "name:$validLeagueName"
    private val invalidSearch: String = "name:$invalidLeagueName"

    private val validLeagueInput: LeagueInput = EC.VALID_LEAGUE_INPUT
    private val invalidLeagueInput: LeagueInput = EC.INVALID_LEAGUE_INPUT

    private val validLeague: League = EC.VALID_LEAGUE
    private val invalidLeague: League = EC.INVALID_LEAGUE

    private val validLeagues: List<League> = listOf(validLeague)

    private val noLeagues: List<League> = emptyList()
    private val noLeagueOutputs: List<LeagueOutput> = emptyList()

    private val validStartSeason: StartSeason = EC.VALID_START_SEASON
    private val invalidStartSeason: StartSeason = EC.INVALID_START_SEASON

    private val validLowerTier: LowerTier = EC.VALID_LOWER_TIER
    private val invalidLowerTier: LowerTier = EC.INVALID_LOWER_TIER

    private val validEndSeason: EndSeason = EC.VALID_END_SEASON
    private val invalidEndSeason: EndSeason = EC.INVALID_END_SEASON

    private val validLeagueSchedule: LeagueSchedule = EC.VALID_LEAGUE_SCHEDULE

    private val validLeagueSearch: LeagueSearch = EC.VALID_LEAGUE_SEARCH
    private val invalidLeagueSearch: LeagueSearch = EC.INVALID_LEAGUE_SEARCH

    private val validMatch: Match = EC.VALID_MATCH

    private val validMatches: List<Match> = listOf(validMatch)

    private val validLeagueDivisionShiftRule: LeagueDivisionShiftRule = EC.VALID_LEAGUE_DIVISION_SHIFT_RULE_INPUT
    private val invalidLeagueDivisionShiftRule: LeagueDivisionShiftRule = EC.INVALID_LEAGUE_DIVISION_SHIFT_RULE_INPUT

    private val validLeagueDivisionShiftUpdate: LeagueDivisionShiftUpdate = EC.VALID_LEAGUE_DIVISION_SHIFT_UPDATE
    private val invalidLeagueDivisionShiftUpdate: LeagueDivisionShiftUpdate = EC.INVALID_LEAGUE_DIVISION_SHIFT_UPDATE

    private val validLeaguePlayoffRuleInput: LeaguePlayoffRuleInput = EC.VALID_LEAGUE_PLAYOFF_RULE_INPUT
    private val invalidLeaguePlayoffRuleInput: LeaguePlayoffRuleInput = EC.INVALID_LEAGUE_PLAYOFF_RULE_INPUT

    private val validPlayoffRule: PlayoffRule = EC.VALID_PLAYOFF_RULE

    private val validPlayoffRules: List<PlayoffRule> = listOf(validPlayoffRule)

    private val noPlayoffRules: List<PlayoffRule> = emptyList()

    private val validLeaguePointRuleInput: LeaguePointRuleInput = EC.VALID_LEAGUE_POINT_RULE_INPUT
    private val invalidLeaguePointRuleInput: LeaguePointRuleInput = EC.INVALID_LEAGUE_POINT_RULE_INPUT

    private val validPointRule: PointRule = EC.VALID_POINT_RULE

    private val validPointRules: List<PointRule> = listOf(validPointRule)

    private val noPointRules: List<PointRule> = emptyList()

    private val validLeagueSpeedrun: LeagueSpeedrun = EC.VALID_LEAGUE_SPEEDRUN
    private val invalidLeagueSpeedrun: LeagueSpeedrun = EC.INVALID_LEAGUE_SPEEDRUN

    private val validLeagueSpeedruns: List<LeagueSpeedrun> = listOf(validLeagueSpeedrun)
    private val noLeagueSpeedruns: List<LeagueSpeedrun> = emptyList()

    private val validDivisionShiftRule: DivisionShiftRule = EC.VALID_DIVISION_SHIFT_RULE

    private val validDivisionShiftRules: List<DivisionShiftRule> = listOf(validDivisionShiftRule)
    private val noDivisionShiftRules: List<DivisionShiftRule> = emptyList()

        @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var playoffService: PlayoffService

    @Mock
    private lateinit var pointService: PointService

    @Mock
    private lateinit var seasonService: SeasonService

    @Mock
    private lateinit var leagueSpeedrunSeekService: LeagueSpeedrunSeekService

    @Mock
    private lateinit var divisionShiftService: DivisionShiftService

    @InjectMocks
    private lateinit var leagueService: LeagueService

    @BeforeAll
    fun setUp() {
        whenever(playoffService.addRules(validLeaguePlayoffRuleInput)).thenReturn(validPlayoffRules)
        whenever(pointService.addRules(validLeaguePointRuleInput)).thenReturn(validPointRules)
        whenever(leagueSeekService.findAllActive(search = validSearch)).thenReturn(validLeagues)
        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        whenever(leagueSeekService.findAllTiers(validLeagueName, validSeason)).thenReturn(validLeagues)
        whenever(leagueSeekService.create(any(League::class))).thenReturn(validLeague)
        whenever((leagueSpeedrunSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel))).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunSeekService.create(validLeagueSpeedrun)).thenReturn(validLeagueSpeedrun)
        doNothing().whenever(seasonService).shiftDivisions(validLeagues)
        whenever(leagueSeekService.find(validLeagueName, validSeason, validParentTierLevel)).thenReturn(validLeague)
        whenever(leagueSeekService.find(validLeagueName, validSeason, validChildTierLevel)).thenReturn(validLeague)
        whenever(seasonService.generateRoundRobin(validLeagueSearch)).thenReturn(validMatches)
        whenever(divisionShiftService.addPromotionRules(validLeagueDivisionShiftRule)).thenReturn(validDivisionShiftRules)
        whenever(divisionShiftService.addRelegationRules(validLeagueDivisionShiftRule)).thenReturn(validDivisionShiftRules)

        whenever(playoffService.addRules(invalidLeaguePlayoffRuleInput)).thenReturn(noPlayoffRules)
        whenever(pointService.addRules(invalidLeaguePointRuleInput)).thenReturn(noPointRules)
        whenever(leagueSeekService.findAllActive(search = invalidSearch)).thenReturn(noLeagues)
        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        whenever(leagueSeekService.findAllTiers(invalidLeagueName, invalidSeason)).thenReturn(noLeagues)
        whenever((leagueSpeedrunSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel))).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunSeekService.create(invalidLeagueSpeedrun)).thenReturn(invalidLeagueSpeedrun)
        doNothing().whenever(seasonService).shiftDivisions(noLeagues)
        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidParentTierLevel)).thenReturn(invalidLeague)
        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidChildTierLevel)).thenReturn(invalidLeague)
        doThrow(IllegalArgumentException::class).whenever(seasonService).generateRoundRobin(invalidLeagueSearch)
        whenever(divisionShiftService.addPromotionRules(invalidLeagueDivisionShiftRule)).thenReturn(noDivisionShiftRules)
        whenever(divisionShiftService.addRelegationRules(invalidLeagueDivisionShiftRule)).thenReturn(noDivisionShiftRules)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid leagueInput - when create - then return valid leagueOutput`() {
            val validLeague: League = EC.VALID_LEAGUE.copy(endedOn = null)
            whenever(leagueSeekService.create(validLeague)).thenReturn(validLeague)

            val expectedLeagueOutput: LeagueOutput = validLeague.output

            val actualLeagueOutput: LeagueOutput = leagueService.create(validLeagueInput)

            actualLeagueOutput shouldEqual expectedLeagueOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid leagueOutputs`() {
            val validLeague: League = EC.VALID_LEAGUE.copy(endedOn = null)
            val validLeagues: List<League> = listOf(validLeague)
            whenever(leagueSeekService.findAllActive(search = validSearch)).thenReturn(validLeagues)

            val expectedLeagueOutputs: List<LeagueOutput> = listOf(validLeague.output)

            val actualLeagueOutputs: Iterable<LeagueOutput> = leagueService.findAll(validSearch)

            actualLeagueOutputs shouldEqual expectedLeagueOutputs
        }

        @Test
        fun `given valid endSeason - when endSeason - then return valid leagueOutput`() {
            val validLeague: League = EC.VALID_LEAGUE.copy(endedOn = null)
            whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)

            val actualLeagueOutput: LeagueOutput = leagueService.endSeason(validEndSeason)

            val expectedLeagueOutput: LeagueOutput = validLeague.output.copy(endedOn = actualLeagueOutput.endedOn)

            actualLeagueOutput shouldEqual expectedLeagueOutput
        }

        @Test
        fun `given valid start season parameters - when startNewSeason - then return valid leagueOutputs`() {
            val validLeague: League = EC.VALID_LEAGUE.copy(endedOn = null)
            whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)

            val expectedLeagueOutputs: List<LeagueOutput> = listOf(validLeague.output)

            val actualLeagueOutputs: Iterable<LeagueOutput> = leagueService.startNewSeason(validStartSeason)

            actualLeagueOutputs shouldEqual expectedLeagueOutputs
        }

        @Test
        fun `given valid lower tier parameters - when addLowerTier - then return valid leagueOutput`() {
            val validLeague: League = EC.VALID_LEAGUE.copy(endedOn = null)
            whenever(leagueSeekService.find(validLeagueName, validSeason, validParentTierLevel)).thenReturn(validLeague)
            whenever(leagueSeekService.create(any(League::class))).thenReturn(validLeague)

            val expectedLeagueOutput: LeagueOutput = validLeague.output

            val actualLeagueOutput: LeagueOutput = leagueService.addLowerTier(validLowerTier)

            actualLeagueOutput shouldEqual expectedLeagueOutput
        }

        @Test
        fun `given valid leagueDivisionShiftUpdate - when modifyDivisionShifts - then return valid leagueOutput`() {
            val validLeague: League = EC.VALID_LEAGUE.copy(endedOn = null)
            whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
            whenever(leagueSeekService.find(validLeagueName, validSeason, validParentTierLevel)).thenReturn(validLeague)
            whenever(leagueSeekService.find(validLeagueName, validSeason, validChildTierLevel)).thenReturn(validLeague)
            whenever(leagueSeekService.create(any(League::class))).thenReturn(validLeague)

            val expectedLeagueOutput: LeagueOutput = validLeague.output

            val actualLeagueOutput: LeagueOutput = leagueService.modifyDivisionShifts(validLeagueDivisionShiftUpdate)

            actualLeagueOutput shouldEqual expectedLeagueOutput
        }

        @Test
        fun `given valid league search - when generateRoundRobin - then return valid leagueSchedule`() {
            val expectedLeagueSchedule: LeagueSchedule = validLeagueSchedule

            val actualLeagueSchedule: LeagueSchedule = leagueService.generateRoundRobin(validLeagueSearch)

            actualLeagueSchedule shouldEqual expectedLeagueSchedule
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid leagueInput - when create - then throw IllegalArgumentException`() {
            doThrow(IllegalArgumentException::class).whenever(leagueSeekService).create(any(League::class))
            invoking { leagueService.create(invalidLeagueInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return invalid leagueOutputs`() {
            val expectedLeagueOutputs: List<LeagueOutput> = noLeagueOutputs

            val actualLeagueOutputs: Iterable<LeagueOutput> = leagueService.findAll(invalidSearch)

            actualLeagueOutputs shouldEqual expectedLeagueOutputs
        }

        @Test
        fun `given invalid endSeason - when endSeason - then throw LeagueHasEndedException`() {
            invoking { leagueService.endSeason(invalidEndSeason) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid start season parameters - when startNewSeason - then return no leagueOutputs`() {
            val expectedLeagueOutputs: List<LeagueOutput> = noLeagueOutputs

            val actualLeagueOutputs: Iterable<LeagueOutput> = leagueService.startNewSeason(invalidStartSeason)

            actualLeagueOutputs shouldEqual expectedLeagueOutputs
        }

        @Test
        fun `given invalid lower tier parameters - when addLowerTier - then throw LeagueHasEndedException`() {
            invoking { leagueService.addLowerTier(invalidLowerTier) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid leagueDivisionShiftUpdate - when modifyDivisionShifts - then throw LeagueHasEndedException`() {
            whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
            whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidParentTierLevel)).thenReturn(invalidLeague)
            whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidChildTierLevel)).thenReturn(invalidLeague)

            invoking { leagueService.modifyDivisionShifts(invalidLeagueDivisionShiftUpdate) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid league search - when generateRoundRobin - then throw IllegalArgumentException`() {
            invoking { leagueService.generateRoundRobin(invalidLeagueSearch) } shouldThrow IllegalArgumentException::class
        }

    }

}
