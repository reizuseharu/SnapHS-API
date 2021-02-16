package com.reizu.snaphs.api.service.seek

import com.reizu.snaphs.api.entity.LeagueRunner
import com.reizu.snaphs.api.repository.LeagueRunnerRepository
import com.reizu.snaphs.api.service.TestConstants as C
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
internal class LeagueRunnerSeekServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validRunnerName: String = C.VALID_RUNNER_NAME
    private val invalidRunnerName: String = C.INVALID_RUNNER_NAME

    private val noLeagueRunners: List<LeagueRunner> = emptyList()

    @Mock
    private lateinit var validLeagueRunner: LeagueRunner

    @Mock
    private lateinit var validLeagueRunners: List<LeagueRunner>

    @Mock
    private lateinit var leagueRunnerRepository: LeagueRunnerRepository

    @InjectMocks
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @BeforeAll
    fun setUp() {
        whenever(leagueRunnerRepository
            .findByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRunnerNameAndRemovedOnIsNull(validLeagueName, validSeason, validTierLevel, validRunnerName)
        ).thenReturn(validLeagueRunner)
        whenever(leagueRunnerRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(validLeagueName, validSeason, validTierLevel)
        ).thenReturn(validLeagueRunners)
        whenever(leagueRunnerRepository.findAllByRunnerNameAndRemovedOnIsNull(validRunnerName)).thenReturn(validLeagueRunners)

        whenever(leagueRunnerRepository
            .findByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRunnerNameAndRemovedOnIsNull(invalidLeagueName, invalidSeason, invalidTierLevel, invalidRunnerName)
        ).thenReturn(null)
        whenever(leagueRunnerRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(invalidLeagueName, invalidSeason, invalidTierLevel)
        ).thenReturn(noLeagueRunners)
        whenever(leagueRunnerRepository.findAllByRunnerNameAndRemovedOnIsNull(invalidRunnerName)).thenReturn(noLeagueRunners)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid parameters - when findByLeagueAndRunner - then return valid leagueRunner`() {
            val expectedLeagueRunner: LeagueRunner = validLeagueRunner

            val actualLeagueRunner = leagueRunnerSeekService.findByLeagueAndRunner(validLeagueName, validSeason, validTierLevel, validRunnerName)

            actualLeagueRunner shouldEqual expectedLeagueRunner
        }

        @Test
        fun `given valid parameters - when findAllByLeague- then return valid leagueRunners`() {
            val expectedLeagueRunners: List<LeagueRunner> = validLeagueRunners

            val actualLeagueRunners = leagueRunnerSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)

            actualLeagueRunners shouldEqual expectedLeagueRunners
        }

        @Test
        fun `given valid parameters - when findAllByRunner - then return valid leagueRunners`() {
            val expectedLeagueRunners: List<LeagueRunner> = validLeagueRunners

            val actualLeagueRunners = leagueRunnerSeekService.findAllByRunner(validRunnerName)

            actualLeagueRunners shouldEqual expectedLeagueRunners
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid parameters - when findByLeagueAndRunner - then throw EntityNotFoundException`() {
            invoking { leagueRunnerSeekService.findByLeagueAndRunner(invalidLeagueName, invalidSeason, invalidTierLevel, invalidRunnerName) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAllByLeague- then return no leagueRunners`() {
            val expectedLeagueRunners: List<LeagueRunner> = noLeagueRunners

            val actualLeagueRunners = leagueRunnerSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualLeagueRunners shouldEqual expectedLeagueRunners
        }

        @Test
        fun `given invalid parameters - when findAllByRunner - then return no leagueRunners`() {
            val expectedLeagueRunners: List<LeagueRunner> = noLeagueRunners

            val actualLeagueRunners = leagueRunnerSeekService.findAllByRunner(invalidRunnerName)

            actualLeagueRunners shouldEqual expectedLeagueRunners
        }

    }

}
