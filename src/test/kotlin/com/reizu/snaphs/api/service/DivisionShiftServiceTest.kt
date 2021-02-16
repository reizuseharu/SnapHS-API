package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.output.QualifiedRunner
import com.reizu.snaphs.api.dto.output.Standing
import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.entity.DivisionShiftRule
import com.reizu.snaphs.api.entity.Shift
import com.reizu.snaphs.api.exception.LeagueHasEndedException
import com.reizu.snaphs.api.dto.input.LeagueDivisionShiftRule as LeagueDivisionShiftRuleInput
import com.reizu.snaphs.api.dto.output.DivisionShiftRule as DivisionShiftRuleOutput
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.DivisionShiftRuleSeekService
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
internal class DivisionShiftServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validShift: Shift = Shift.PROMOTION
    private val invalidShift: Shift = Shift.RELEGATION

    private val validPromotions: Int = C.VALID_PROMOTIONS
    private val invalidPromotions: Int = C.INVALID_PROMOTIONS

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

    private val validLeagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput = EC.VALID_LEAGUE_DIVISION_SHIFT_RULE_INPUT
    private val invalidLeagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput = EC.INVALID_LEAGUE_DIVISION_SHIFT_RULE_INPUT

    private val validDivisionShiftRule: DivisionShiftRule = EC.VALID_DIVISION_SHIFT_RULE
    private val invalidDivisionShiftRule: DivisionShiftRule = EC.INVALID_DIVISION_SHIFT_RULE

    private val validDivisionShiftRules: List<DivisionShiftRule> = listOf(validDivisionShiftRule)
    private val validDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRules.map { divisionShiftRule -> divisionShiftRule.output }

    private val invalidDivisionShiftRules: List<DivisionShiftRule> = listOf(invalidDivisionShiftRule)

    private val noDivisionShiftRules: List<DivisionShiftRule> = emptyList()
    private val noDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = emptyList()

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var divisionShiftRuleSeekService: DivisionShiftRuleSeekService

    @Mock
    private lateinit var leagueService: LeagueService

    @InjectMocks
    private lateinit var divisionShiftService: DivisionShiftService

    @BeforeAll
    fun setUp() {
        whenever(validLeague.divisionShiftRules).thenReturn(validDivisionShiftRules)
        whenever(validLeague.promotions).thenReturn(validPromotions)
        whenever(validLeague.promotionRules).thenReturn(validDivisionShiftRules)
        whenever(validLeague.endedOn).thenReturn(validEndedOn)

        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        doNothing().whenever(leagueService).validateLeagueChange(validEndedOn)
        whenever(divisionShiftRuleSeekService.create(any())).thenReturn(validDivisionShiftRule)
        whenever(divisionShiftRuleSeekService.findAllActive(search = validSearch)).thenReturn(validDivisionShiftRules)
        whenever(divisionShiftRuleSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)).thenReturn(validDivisionShiftRules)
        doNothing().whenever(divisionShiftRuleSeekService).deleteAll(validDivisionShiftRules)
        whenever(divisionShiftRuleSeekService.findAllPromotionByLeague(validLeagueName, validSeason, validTierLevel)).thenReturn(validDivisionShiftRules)
        whenever(divisionShiftRuleSeekService.findAllRelegationByLeague(validLeagueName, validSeason, validTierLevel)).thenReturn(validDivisionShiftRules)

        whenever(invalidLeague.divisionShiftRules).thenReturn(noDivisionShiftRules)
        whenever(invalidLeague.relegations).thenReturn(invalidPromotions)
        whenever(invalidLeague.relegationRules).thenReturn(invalidDivisionShiftRules)
        whenever(invalidLeague.endedOn).thenReturn(invalidEndedOn)

        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        doThrow(LeagueHasEndedException::class).whenever(leagueService).validateLeagueChange(invalidEndedOn)
        whenever(divisionShiftRuleSeekService.findAllActive(search = invalidSearch)).thenReturn(noDivisionShiftRules)
        whenever(divisionShiftRuleSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(noDivisionShiftRules)
        whenever(divisionShiftRuleSeekService.findAllPromotionByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(noDivisionShiftRules)
        whenever(divisionShiftRuleSeekService.findAllRelegationByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(noDivisionShiftRules)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when addDivisionShiftRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = validDivisionShiftRules

            val actualDivisionShiftRules: List<DivisionShiftRule> = divisionShiftService.addDivisionShiftRules(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when addPromotionRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = validDivisionShiftRules

            val actualDivisionShiftRules: List<DivisionShiftRule> = divisionShiftService.addPromotionRules(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when addRelegationRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = validDivisionShiftRules

            val actualDivisionShiftRules: List<DivisionShiftRule> = divisionShiftService.addRelegationRules(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given valid parameters - when matchQualifiedRunners - then return valid qualifiedRunners`() {
            val expectedQualifiedRunners: List<QualifiedRunner> = validQualifiedRunners

            val actualQualifiedRunners: List<QualifiedRunner> = divisionShiftService.matchQualifiedRunners(validLeagueName, validSeason, validTierLevel, validStandings, validShift)

            actualQualifiedRunners shouldEqual expectedQualifiedRunners
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when create - then return valid divisionShiftRuleOutputs`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: Iterable<DivisionShiftRuleOutput> = divisionShiftService.create(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid search - when findAll - then return valid divisionShiftRuleOutputs`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: Iterable<DivisionShiftRuleOutput> = divisionShiftService.findAll(validSearch)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid league parameters - when findAll - then return valid divisionShiftRuleOutputs`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: Iterable<DivisionShiftRuleOutput> = divisionShiftService.findAll(validLeagueName, validSeason, validTierLevel)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when replace - then return valid divisionShiftRuleOutputs`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: Iterable<DivisionShiftRuleOutput> = divisionShiftService.replace(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when createPromotionRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = divisionShiftService.createPromotionRules(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when findAllPromotionRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = divisionShiftService.findAllPromotionRules(validLeagueName, validSeason, validTierLevel)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when replacePromotionRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = divisionShiftService.replacePromotionRules(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when createRelegationRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = divisionShiftService.createRelegationRules(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when findAllRelegationRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = divisionShiftService.findAllRelegationRules(validLeagueName, validSeason, validTierLevel)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given valid leagueDivisionShiftRuleInput - when replaceRelegationRules - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = validDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = divisionShiftService.replaceRelegationRules(validLeagueDivisionShiftRuleInput)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given ended league(s) - when addDivisionShiftRules - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.addDivisionShiftRules(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given ended league(s) - when addPromotionRules - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.addPromotionRules(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given ended league(s) - when addRelegationRules - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.addRelegationRules(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid parameters - when matchQualifiedRunners - then return no qualifiedRunners`() {
            val expectedQualifiedRunners: List<QualifiedRunner> = noQualifiedRunners

            val actualQualifiedRunners: List<QualifiedRunner> = divisionShiftService.matchQualifiedRunners(invalidLeagueName, invalidSeason, invalidTierLevel, noStandings, invalidShift)

            actualQualifiedRunners shouldEqual expectedQualifiedRunners
        }

        @Test
        fun `given ended league(s) - when create - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.create(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no divisionShiftRuleOutputs`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = noDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: Iterable<DivisionShiftRuleOutput> = divisionShiftService.findAll(invalidSearch)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given invalid league parameters - when findAll - then return no divisionShiftRuleOutputs`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = noDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: Iterable<DivisionShiftRuleOutput> = divisionShiftService.findAll(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given ended league(s) - when replace - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.replace(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given ended league(s) - when createPromotionRules - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.createPromotionRules(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid league parameters - when findAllPromotionRules - then return no divisionShiftRuleOutputs`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = noDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = divisionShiftService.findAllPromotionRules(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given ended league(s) - when replacePromotionRules - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.replacePromotionRules(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given ended league(s) - when createRelegationRules - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.createRelegationRules(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid league parameters  - when findAllRelegationRules - then then return no divisionShiftRuleOutputs`() {
            val expectedDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = noDivisionShiftRuleOutputs

            val actualDivisionShiftRuleOutputs: List<DivisionShiftRuleOutput> = divisionShiftService.findAllRelegationRules(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualDivisionShiftRuleOutputs shouldEqual expectedDivisionShiftRuleOutputs
        }

        @Test
        fun `given ended league(s) - when replaceRelegationRules - then throw LeagueHasEndedException`() {
            invoking { divisionShiftService.replaceRelegationRules(invalidLeagueDivisionShiftRuleInput) } shouldThrow LeagueHasEndedException::class
        }

    }

}
