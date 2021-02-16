package com.reizu.snaphs.api.service.seek

import com.reizu.snaphs.api.entity.DivisionShiftRule
import com.reizu.snaphs.api.entity.Shift
import com.reizu.snaphs.api.repository.DivisionShiftRuleRepository
import com.reizu.snaphs.api.service.TestConstants as C
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
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
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DivisionShiftRuleSeekServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val noDivisionShiftRules: List<DivisionShiftRule> = emptyList()

    @Mock
    private lateinit var validDivisionShiftRule: DivisionShiftRule

    @Mock
    private lateinit var validDivisionShiftRules: List<DivisionShiftRule>

    @Mock
    private lateinit var invalidDivisionShiftRule: DivisionShiftRule

    @Mock
    private lateinit var invalidDivisionShiftRules: List<DivisionShiftRule>

    @Mock
    private lateinit var divisionShiftRuleRepository: DivisionShiftRuleRepository

    @InjectMocks
    private lateinit var divisionShiftRuleSeekService: DivisionShiftRuleSeekService

    @BeforeAll
    fun setUp() {
        whenever(divisionShiftRuleRepository.findByLeagueNameAndRemovedOnIsNull(validLeagueName)).thenReturn(validDivisionShiftRule)
        whenever(divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(validLeagueName, validSeason, validTierLevel)
        ).thenReturn(validDivisionShiftRules)
        whenever(divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(validLeagueName, validSeason, validTierLevel, Shift.PROMOTION)
        ).thenReturn(validDivisionShiftRules)
        whenever(divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(validLeagueName, validSeason, validTierLevel, Shift.RELEGATION)
        ).thenReturn(validDivisionShiftRules)
        doNothing().whenever(divisionShiftRuleRepository).delete(validDivisionShiftRule)
        doNothing().whenever(divisionShiftRuleRepository).deleteAll(invalidDivisionShiftRules)

        whenever(divisionShiftRuleRepository.findByLeagueNameAndRemovedOnIsNull(invalidLeagueName)).thenReturn(null)
        whenever(divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(invalidLeagueName, invalidSeason, invalidTierLevel)
        ).thenReturn(noDivisionShiftRules)
        whenever(divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(invalidLeagueName, invalidSeason, invalidTierLevel, Shift.RELEGATION)
        ).thenReturn(noDivisionShiftRules)
        whenever(divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(invalidLeagueName, invalidSeason, invalidTierLevel, Shift.RELEGATION)
        ).thenReturn(noDivisionShiftRules)
        whenever(divisionShiftRuleRepository.delete(invalidDivisionShiftRule)).thenThrow(EntityNotFoundException::class.java)
        whenever(divisionShiftRuleRepository.deleteAll(invalidDivisionShiftRules)).thenThrow(EntityNotFoundException::class.java)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid league name - when findByLeague - then return valid divisionShiftRule`() {
            val expectedDivisionShiftRule: DivisionShiftRule = validDivisionShiftRule

            val actualDivisionShiftRule = divisionShiftRuleSeekService.findByLeague(validLeagueName)

            actualDivisionShiftRule shouldEqual expectedDivisionShiftRule
        }

        @Test
        fun `given valid parameters - when findAllByLeague- then return valid divisionShiftRules`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = validDivisionShiftRules

            val actualDivisionShiftRules = divisionShiftRuleSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given valid parameters - when findAllPromotionsByLeague - then return valid divisionShiftRules`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = validDivisionShiftRules

            val actualDivisionShiftRules = divisionShiftRuleSeekService.findAllPromotionByLeague(validLeagueName, validSeason, validTierLevel)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given valid parameters - when findAllRelegationsByLeague- then return valid divisionShiftRules`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = validDivisionShiftRules

            val actualDivisionShiftRules = divisionShiftRuleSeekService.findAllRelegationByLeague(validLeagueName, validSeason, validTierLevel)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given valid divisionShiftRule - when delete - then return nothing`() {
            divisionShiftRuleSeekService.delete(validDivisionShiftRule)

            verify(divisionShiftRuleRepository).delete(validDivisionShiftRule)
        }

        @Test
        fun `given valid divisionShiftRules - when deleteAll - then return nothing`() {
            divisionShiftRuleSeekService.deleteAll(validDivisionShiftRules)

            verify(divisionShiftRuleRepository).deleteAll(validDivisionShiftRules)
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid league name - when findByLeague - then throw EntityNotFoundException`() {
            invoking { divisionShiftRuleSeekService.findByLeague(invalidLeagueName) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAllByLeague- then return no divisionShiftRules`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = noDivisionShiftRules

            val actualDivisionShiftRules = divisionShiftRuleSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given invalid league name - when findAllPromotionsByLeague - then return no divisionShiftRulesn`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = noDivisionShiftRules

            val actualDivisionShiftRules = divisionShiftRuleSeekService.findAllPromotionByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given invalid league name - when findAllRelegationsByLeague - then return no divisionShiftRules`() {
            val expectedDivisionShiftRules: List<DivisionShiftRule> = noDivisionShiftRules

            val actualDivisionShiftRules = divisionShiftRuleSeekService.findAllRelegationByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualDivisionShiftRules shouldEqual expectedDivisionShiftRules
        }

        @Test
        fun `given invalid divisionShiftRule - when delete - then throw EntityNotFoundException`() {
            invoking { divisionShiftRuleSeekService.delete(invalidDivisionShiftRule) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid divisionShiftRules - when deleteAll - then throw EntityNotFoundException`() {
            invoking { divisionShiftRuleSeekService.deleteAll(invalidDivisionShiftRules) } shouldThrow EntityNotFoundException::class
        }

    }

}
