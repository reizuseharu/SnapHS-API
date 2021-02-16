package com.reizu.snaphs.api.service.seek

import com.reizu.snaphs.api.entity.PointRule
import com.reizu.snaphs.api.repository.PointRuleRepository
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
internal class PointRuleSeekServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val noPointRules: List<PointRule> = emptyList()

    @Mock
    private lateinit var validPointRule: PointRule

    @Mock
    private lateinit var validPointRules: List<PointRule>

    @Mock
    private lateinit var invalidPointRule: PointRule

    @Mock
    private lateinit var invalidPointRules: List<PointRule>

    @Mock
    private lateinit var pointRuleRepository: PointRuleRepository

    @InjectMocks
    private lateinit var pointRuleSeekService: PointRuleSeekService

    @BeforeAll
    fun setUp() {
        whenever(pointRuleRepository.findByLeagueNameAndRemovedOnIsNull(validLeagueName)).thenReturn(validPointRule)
        whenever(pointRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(validLeagueName, validSeason, validTierLevel)
        ).thenReturn(validPointRules)
        doNothing().whenever(pointRuleRepository).delete(validPointRule)
        doNothing().whenever(pointRuleRepository).deleteAll(invalidPointRules)

        whenever(pointRuleRepository.findByLeagueNameAndRemovedOnIsNull(invalidLeagueName)).thenReturn(null)
        whenever(pointRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(invalidLeagueName, invalidSeason, invalidTierLevel)
        ).thenReturn(noPointRules)
        whenever(pointRuleRepository.delete(invalidPointRule)).thenThrow(EntityNotFoundException::class.java)
        whenever(pointRuleRepository.deleteAll(invalidPointRules)).thenThrow(EntityNotFoundException::class.java)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid league name - when findByLeague - then return valid pointRule`() {
            val expectedPointRule: PointRule = validPointRule

            val actualPointRule = pointRuleSeekService.findByLeague(validLeagueName)

            actualPointRule shouldEqual expectedPointRule
        }

        @Test
        fun `given valid parameters - when findAllByLeague- then return valid pointRules`() {
            val expectedPointRules: List<PointRule> = validPointRules

            val actualPointRules = pointRuleSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)

            actualPointRules shouldEqual expectedPointRules
        }

        @Test
        fun `given valid pointRule - when delete - then return nothing`() {
            pointRuleSeekService.delete(validPointRule)

            verify(pointRuleRepository).delete(validPointRule)
        }

        @Test
        fun `given valid pointRules - when deleteAll - then return nothing`() {
            pointRuleSeekService.deleteAll(validPointRules)

            verify(pointRuleRepository).deleteAll(validPointRules)
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid league name - when findByLeague - then throw EntityNotFoundException`() {
            invoking { pointRuleSeekService.findByLeague(invalidLeagueName) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAllByLeague- then return no pointRules`() {
            val expectedPointRules: List<PointRule> = noPointRules

            val actualPointRules = pointRuleSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualPointRules shouldEqual expectedPointRules
        }

        @Test
        fun `given invalid pointRule - when delete - then throw EntityNotFoundException`() {
            invoking { pointRuleSeekService.delete(invalidPointRule) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid pointRules - when deleteAll - then throw EntityNotFoundException`() {
            invoking { pointRuleSeekService.deleteAll(invalidPointRules) } shouldThrow EntityNotFoundException::class
        }

    }

}
