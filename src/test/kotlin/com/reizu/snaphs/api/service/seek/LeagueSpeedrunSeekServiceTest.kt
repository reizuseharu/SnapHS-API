package com.reizu.snaphs.api.service.seek

import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.LeagueSpeedrun
import com.reizu.snaphs.api.entity.LeagueType
import com.reizu.snaphs.api.repository.LeagueSpeedrunRepository
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
internal class LeagueSpeedrunSeekServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validLeagueType: LeagueType = C.VALID_LEAGUE_TYPE
    private val invalidLeagueType: LeagueType = C.INVALID_LEAGUE_TYPE

    private val validGameName: String = C.VALID_GAME_NAME
    private val invalidGameName: String = C.INVALID_GAME_NAME

    private val validSystemName: String = C.VALID_SYSTEM_NAME
    private val invalidSystemName: String = C.INVALID_SYSTEM_NAME

    private val validRegion: Region = C.VALID_REGION
    private val invalidRegion: Region = C.INVALID_REGION

    private val validCategory: String = C.VALID_CATEGORY
    private val invalidCategory: String = C.INVALID_CATEGORY

    private val validIsEmulated: Boolean = C.VALID_IS_EMULATED
    private val invalidIsEmulated: Boolean = C.INVALID_IS_EMULATED

    private val validVersion: String = C.VALID_VERSION
    private val invalidVersion: String = C.INVALID_VERSION

    private val noLeagueSpeedruns: List<LeagueSpeedrun> = emptyList()

    @Mock
    private lateinit var validLeagueSpeedrun: LeagueSpeedrun

    @Mock
    private lateinit var validLeagueSpeedruns: List<LeagueSpeedrun>

    @Mock
    private lateinit var leagueSpeedrunRepository: LeagueSpeedrunRepository

    @InjectMocks
    private lateinit var leagueSpeedrunSeekService: LeagueSpeedrunSeekService

    @BeforeAll
    fun setUp() {
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndRemovedOnIsNull(validGameName)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartSystemNameAndRemovedOnIsNull(validSystemName)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartRegionAndRemovedOnIsNull(validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartGameNameAndRemovedOnIsNull(validLeagueName, validGameName)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(validLeagueName, validSystemName)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartRegionAndRemovedOnIsNull(validLeagueName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndRemovedOnIsNull(validCategory, validGameName)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(validGameName, validSystemName)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(validGameName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(validSystemName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(validLeagueName, validGameName, validSystemName)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(validLeagueName, validGameName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(validLeagueName, validSystemName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(validCategory, validGameName, validSystemName)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(validCategory, validGameName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(validGameName, validSystemName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(validLeagueName, validCategory, validGameName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(validLeagueName, validGameName, validSystemName, validRegion)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunRepository
            .findFirstByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersionAndRemovedOnIsNull(validLeagueName, validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)
        ).thenReturn(validLeagueSpeedrun)

        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndRemovedOnIsNull(invalidGameName)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartSystemNameAndRemovedOnIsNull(invalidSystemName)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartRegionAndRemovedOnIsNull(invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartGameNameAndRemovedOnIsNull(invalidLeagueName, invalidGameName)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(invalidLeagueName, invalidSystemName)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidLeagueName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndRemovedOnIsNull(invalidCategory, invalidGameName)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(invalidGameName, invalidSystemName)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidGameName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidSystemName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(invalidLeagueName, invalidGameName, invalidSystemName)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidLeagueName, invalidGameName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidLeagueName, invalidSystemName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(invalidCategory, invalidGameName, invalidSystemName)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidCategory, invalidGameName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidGameName, invalidSystemName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidLeagueName, invalidCategory, invalidGameName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository.findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(invalidLeagueName, invalidGameName, invalidSystemName, invalidRegion)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunRepository
            .findFirstByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersionAndRemovedOnIsNull(invalidLeagueName, invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)
        ).thenReturn(null)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid game name - when findAllByGame - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByGame(validGameName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid system name - when findAllBySystem - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllBySystem(validSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid region - when findAllByRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByRegion(validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid game name - when findAllByLeagueAndGame - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndGame(validLeagueName, validGameName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid system name - when findAllByLeagueAndSystem - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndSystem(validLeagueName, validSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid region - when findAllByLeagueAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndRegion(validLeagueName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByCategoryAndGame - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByCategoryAndGame(validCategory, validGameName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByGameAndSystem - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByGameAndSystem(validGameName, validSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByGameAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByGameAndRegion(validGameName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllBySystemAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllBySystemAndRegion(validSystemName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByLeagueAndGameAndSystem - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndGameAndSystem(validLeagueName, validGameName, validSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByLeagueAndGameAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndGameAndRegion(validLeagueName, validGameName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByLeagueAndSystemAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndSystemAndRegion(validLeagueName, validSystemName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByCategoryAndGameAndSystem - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByCategoryAndGameAndSystem(validCategory, validGameName, validSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByCategoryAndGameAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByCategoryAndGameAndRegion(validCategory, validGameName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByGameAndSystemAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByGameAndSystemAndRegion(validGameName, validSystemName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByLeagueAndCategoryAndGameAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndCategoryAndGameAndRegion(validLeagueName, validCategory, validGameName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByLeagueAndGameAndSystemAndRegion - then return valid leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndGameAndSystemAndRegion(validLeagueName, validGameName, validSystemName, validRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given valid parameters - when find - then return valid leagueSpeedrun`() {
            val expectedLeagueSpeedrun:LeagueSpeedrun = validLeagueSpeedrun

            val actualLeagueSpeedrun: LeagueSpeedrun = leagueSpeedrunSeekService.find(validLeagueName, validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualLeagueSpeedrun shouldEqual expectedLeagueSpeedrun
        }

        @Test
        fun `given valid parameters - when findAll - then return valid leagueSpeedruns`() {
            // ? Think of method to allow filtering on mock
            whenever(leagueSpeedrunRepository.findAllByRemovedOnIsNull()).thenReturn(validLeagueSpeedruns)
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = validLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAll(validLeagueName, validLeagueType, validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid game name - when findAllByGame - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByGame(invalidGameName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid system name - when findAllBySystem - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllBySystem(invalidSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid region - when findAllByRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByRegion(invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid game name - when findAllByLeagueAndGame - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndGame(invalidLeagueName, invalidGameName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid system name - when findAllByLeagueAndSystem - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndSystem(invalidLeagueName, invalidSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid region - when findAllByLeagueAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndRegion(invalidLeagueName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByCategoryAndGame - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByCategoryAndGame(invalidCategory, invalidGameName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByGameAndSystem - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByGameAndSystem(invalidGameName, invalidSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByGameAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByGameAndRegion(invalidGameName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllBySystemAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllBySystemAndRegion(invalidSystemName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByLeagueAndGameAndSystem - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndGameAndSystem(invalidLeagueName, invalidGameName, invalidSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByLeagueAndGameAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndGameAndRegion(invalidLeagueName, invalidGameName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByLeagueAndSystemAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndSystemAndRegion(invalidLeagueName, invalidSystemName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByCategoryAndGameAndSystem - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByCategoryAndGameAndSystem(invalidCategory, invalidGameName, invalidSystemName)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByCategoryAndGameAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByCategoryAndGameAndRegion(invalidCategory, invalidGameName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByGameAndSystemAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByGameAndSystemAndRegion(invalidGameName, invalidSystemName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByLeagueAndCategoryAndGameAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndCategoryAndGameAndRegion(invalidLeagueName, invalidCategory, invalidGameName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByLeagueAndGameAndSystemAndRegion - then return no leagueSpeedruns`() {
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAllByLeagueAndGameAndSystemAndRegion(invalidLeagueName, invalidGameName, invalidSystemName, invalidRegion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

        @Test
        fun `given invalid parameters - when find - then return invalid leagueSpeedrun`() {
            invoking {
                leagueSpeedrunSeekService.find(invalidLeagueName, invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)
            } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAll - then return no leagueSpeedruns`() {
            whenever(leagueSpeedrunRepository.findAllByRemovedOnIsNull()).thenReturn(noLeagueSpeedruns)
            val expectedLeagueSpeedruns: List<LeagueSpeedrun> = noLeagueSpeedruns

            val actualLeagueSpeedruns: List<LeagueSpeedrun> = leagueSpeedrunSeekService.findAll(invalidLeagueName, invalidLeagueType, invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)

            actualLeagueSpeedruns shouldEqual expectedLeagueSpeedruns
        }

    }

}
