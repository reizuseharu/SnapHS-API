package com.reizu.snaphs.api.service.seek

import com.reizu.snaphs.api.entity.PlayoffRule
import com.reizu.snaphs.api.repository.PlayoffRuleRepository
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
internal class PlayoffRuleSeekServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val noPlayoffRules: List<PlayoffRule> = emptyList()

    @Mock
    private lateinit var validPlayoffRule: PlayoffRule

    @Mock
    private lateinit var validPlayoffRules: List<PlayoffRule>

    @Mock
    private lateinit var invalidPlayoffRule: PlayoffRule

    @Mock
    private lateinit var invalidPlayoffRules: List<PlayoffRule>

    @Mock
    private lateinit var playoffRuleRepository: PlayoffRuleRepository

    @InjectMocks
    private lateinit var playoffRuleSeekService: PlayoffRuleSeekService

    @BeforeAll
    fun setUp() {
        whenever(playoffRuleRepository.findByLeagueNameAndRemovedOnIsNull(validLeagueName)).thenReturn(validPlayoffRule)
        whenever(playoffRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(validLeagueName, validSeason, validTierLevel)
        ).thenReturn(validPlayoffRules)
        doNothing().whenever(playoffRuleRepository).delete(validPlayoffRule)
        doNothing().whenever(playoffRuleRepository).deleteAll(invalidPlayoffRules)

        whenever(playoffRuleRepository.findByLeagueNameAndRemovedOnIsNull(invalidLeagueName)).thenReturn(null)
        whenever(playoffRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(invalidLeagueName, invalidSeason, invalidTierLevel)
        ).thenReturn(noPlayoffRules)
        whenever(playoffRuleRepository.delete(invalidPlayoffRule)).thenThrow(EntityNotFoundException::class.java)
        whenever(playoffRuleRepository.deleteAll(invalidPlayoffRules)).thenThrow(EntityNotFoundException::class.java)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid league name - when findByLeague - then return valid playoffRule`() {
            val expectedPlayoffRule: PlayoffRule = validPlayoffRule

            val actualPlayoffRule = playoffRuleSeekService.findByLeague(validLeagueName)

            actualPlayoffRule shouldEqual expectedPlayoffRule
        }

        @Test
        fun `given valid parameters - when findAllByLeague- then return valid playoffRules`() {
            val expectedPlayoffRules: List<PlayoffRule> = validPlayoffRules

            val actualPlayoffRules = playoffRuleSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)

            actualPlayoffRules shouldEqual expectedPlayoffRules
        }

        @Test
        fun `given valid playoffRule - when delete - then return nothing`() {
            playoffRuleSeekService.delete(validPlayoffRule)

            verify(playoffRuleRepository).delete(validPlayoffRule)
        }

        @Test
        fun `given valid playoffRules - when deleteAll - then return nothing`() {
            playoffRuleSeekService.deleteAll(validPlayoffRules)

            verify(playoffRuleRepository).deleteAll(validPlayoffRules)
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid league name - when findByLeague - then throw EntityNotFoundException`() {
            invoking { playoffRuleSeekService.findByLeague(invalidLeagueName) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAllByLeague- then return no playoffRules`() {
            val expectedPlayoffRules: List<PlayoffRule> = noPlayoffRules

            val actualPlayoffRules = playoffRuleSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualPlayoffRules shouldEqual expectedPlayoffRules
        }

        @Test
        fun `given invalid playoffRule - when delete - then throw EntityNotFoundException`() {
            invoking { playoffRuleSeekService.delete(invalidPlayoffRule) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid playoffRules - when deleteAll - then throw EntityNotFoundException`() {
            invoking { playoffRuleSeekService.deleteAll(invalidPlayoffRules) } shouldThrow EntityNotFoundException::class
        }

    }

}
