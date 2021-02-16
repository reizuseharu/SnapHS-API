package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.entity.PointRule
import com.reizu.snaphs.api.exception.LeagueHasEndedException
import com.reizu.snaphs.api.dto.input.LeaguePointRule as LeaguePointRuleInput
import com.reizu.snaphs.api.dto.output.PointRule as PointRuleOutput
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.PointRuleSeekService
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

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PointServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validSearch: String = "leagueName:$validLeagueName"
    private val invalidSearch: String = "leagueName:$invalidLeagueName"

    private val validLeague: League = EC.VALID_LEAGUE
    private val invalidLeague: League = EC.INVALID_LEAGUE

    private val validLeaguePointRuleInput: LeaguePointRuleInput = EC.VALID_LEAGUE_POINT_RULE_INPUT
    private val invalidLeaguePointRuleInput: LeaguePointRuleInput = EC.INVALID_LEAGUE_POINT_RULE_INPUT

    private val validPointRule: PointRule = EC.VALID_POINT_RULE

    private val validPointRules: List<PointRule> = listOf(validPointRule)
    private val validPointRuleOutputs: List<PointRuleOutput> = validPointRules.map { pointRule -> pointRule.output }

    private val noPointRules: List<PointRule> = emptyList()
    private val noPointRuleOutputs: List<PointRuleOutput> = emptyList()

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var pointRuleSeekService: PointRuleSeekService

    @Mock
    private lateinit var leagueService: LeagueService

    @InjectMocks
    private lateinit var pointService: PointService

    @BeforeAll
    fun setUp() {
        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        doNothing().whenever(leagueService).validateLeagueChange(validLeague.endedOn)
        whenever(pointRuleSeekService.create(any())).thenReturn(validPointRule)
        whenever(pointRuleSeekService.findAllActive(search = validSearch)).thenReturn(validPointRules)
        whenever(pointRuleSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)).thenReturn(validPointRules)
        doNothing().whenever(pointRuleSeekService).deleteAll(validPointRules)

        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        doThrow(LeagueHasEndedException::class).whenever(leagueService).validateLeagueChange(invalidLeague.endedOn)
        whenever(pointRuleSeekService.findAllActive(search = invalidSearch)).thenReturn(noPointRules)
        whenever(pointRuleSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(noPointRules)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid leaguePointRuleInput - when addRules - then return valid pointRules`() {
            val expectedPointRules: List<PointRule> = validPointRules

            val actualPointRules: List<PointRule> = pointService.addRules(validLeaguePointRuleInput)

            actualPointRules shouldEqual expectedPointRules
        }

        @Test
        fun `given valid leaguePointRuleInput - when create - then return valid pointRuleOutputs`() {
            val expectedPointRuleOutputs: List<PointRuleOutput> = validPointRuleOutputs

            val actualPointRuleOutputs: Iterable<PointRuleOutput> = pointService.create(validLeaguePointRuleInput)

            actualPointRuleOutputs shouldEqual expectedPointRuleOutputs
        }

        @Test
        fun `given valid search - when findAll - then return valid pointRuleOutputs`() {
            val expectedPointRuleOutputs: List<PointRuleOutput> = validPointRuleOutputs

            val actualPointRuleOutputs: Iterable<PointRuleOutput> = pointService.findAll(validSearch)

            actualPointRuleOutputs shouldEqual expectedPointRuleOutputs
        }

        @Test
        fun `given valid league parameters - when findAll - then return valid pointRuleOutputs`() {
            val expectedPointRuleOutputs: List<PointRuleOutput> = validPointRuleOutputs

            val actualPointRuleOutputs: Iterable<PointRuleOutput> = pointService.findAll(validLeagueName, validSeason, validTierLevel)

            actualPointRuleOutputs shouldEqual expectedPointRuleOutputs
        }

        @Test
        fun `given valid leaguePointRuleInput - when replace - then return valid pointRuleOutputs`() {
            val expectedPointRuleOutputs: List<PointRuleOutput> = validPointRuleOutputs

            val actualPointRuleOutputs: Iterable<PointRuleOutput> = pointService.replace(validLeaguePointRuleInput)

            actualPointRuleOutputs shouldEqual expectedPointRuleOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given ended league(s) - when addRules - then throw LeagueHasEndedException`() {
            invoking { pointService.addRules(invalidLeaguePointRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given ended league(s) - when create - then throw LeagueHasEndedException`() {
            invoking { pointService.create(invalidLeaguePointRuleInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no pointRuleOutputs`() {
            val expectedPointRuleOutputs: List<PointRuleOutput> = noPointRuleOutputs

            val actualPointRuleOutputs: Iterable<PointRuleOutput> = pointService.findAll(invalidSearch)

            actualPointRuleOutputs shouldEqual expectedPointRuleOutputs
        }

        @Test
        fun `given invalid league parameters - when findAll - then return no pointRuleOutputs`() {
            val expectedPointRuleOutputs: List<PointRuleOutput> = noPointRuleOutputs

            val actualPointRuleOutputs: Iterable<PointRuleOutput> = pointService.findAll(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualPointRuleOutputs shouldEqual expectedPointRuleOutputs
        }

        @Test
        fun `given ended league(s) - when replace - then throw LeagueHasEndedException`() {
            invoking { pointService.replace(invalidLeaguePointRuleInput) } shouldThrow LeagueHasEndedException::class
        }

    }

}
