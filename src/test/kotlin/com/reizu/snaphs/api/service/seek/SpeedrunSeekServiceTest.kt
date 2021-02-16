package com.reizu.snaphs.api.service.seek

import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.Speedrun
import com.reizu.snaphs.api.repository.SpeedrunRepository
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
internal class SpeedrunSeekServiceTest {

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

    private val noSpeedruns: List<Speedrun> = emptyList()

    @Mock
    private lateinit var validSpeedrun: Speedrun

    @Mock
    private lateinit var validSpeedruns: List<Speedrun>

    @Mock
    private lateinit var speedrunRepository: SpeedrunRepository

    @InjectMocks
    private lateinit var speedrunSeekService: SpeedrunSeekService

    @BeforeAll
    fun setUp() {
        whenever(speedrunRepository.findAllByCartGameNameAndRemovedOnIsNull(validGameName)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCartSystemNameAndRemovedOnIsNull(validSystemName)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCartRegionAndRemovedOnIsNull(validRegion)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCategoryAndCartGameNameAndRemovedOnIsNull(validCategory, validGameName)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCartGameNameAndCartSystemNameAndRemovedOnIsNull(validGameName, validSystemName)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCartGameNameAndCartRegionAndRemovedOnIsNull(validGameName, validRegion)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCartSystemNameAndCartRegionAndRemovedOnIsNull(validSystemName, validRegion)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCategoryAndCartGameNameAndCartSystemNameAndRemovedOnIsNull(validCategory, validGameName, validSystemName)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCategoryAndCartGameNameAndCartRegionAndRemovedOnIsNull(validCategory, validGameName, validRegion)).thenReturn(validSpeedruns)
        whenever(speedrunRepository.findAllByCartGameNameAndCartSystemNameAndCartRegionAndRemovedOnIsNull(validGameName, validSystemName, validRegion)).thenReturn(validSpeedruns)
        whenever(speedrunRepository
            .findFirstByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndCartVersionAndRemovedOnIsNull(validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)
        ).thenReturn(validSpeedrun)
        
        whenever(speedrunRepository.findAllByCartGameNameAndRemovedOnIsNull(invalidGameName)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCartSystemNameAndRemovedOnIsNull(invalidSystemName)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCartRegionAndRemovedOnIsNull(invalidRegion)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCategoryAndCartGameNameAndRemovedOnIsNull(invalidCategory, invalidGameName)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCartGameNameAndCartSystemNameAndRemovedOnIsNull(invalidGameName, invalidSystemName)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCartGameNameAndCartRegionAndRemovedOnIsNull(invalidGameName, invalidRegion)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCartSystemNameAndCartRegionAndRemovedOnIsNull(invalidSystemName, invalidRegion)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCategoryAndCartGameNameAndCartSystemNameAndRemovedOnIsNull(invalidCategory, invalidGameName, invalidSystemName)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCategoryAndCartGameNameAndCartRegionAndRemovedOnIsNull(invalidCategory, invalidGameName, invalidRegion)).thenReturn(noSpeedruns)
        whenever(speedrunRepository.findAllByCartGameNameAndCartSystemNameAndCartRegionAndRemovedOnIsNull(invalidGameName, invalidSystemName, invalidRegion)).thenReturn(noSpeedruns)
        whenever(speedrunRepository
            .findFirstByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndCartVersionAndRemovedOnIsNull(invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)
        ).thenReturn(null)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid game name - when findAllByGame - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByGame(validGameName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid system name - when findAllBySystem - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllBySystem(validSystemName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid region - when findAllByRegion - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByRegion(validRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByCategoryAndGame - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByCategoryAndGame(validCategory, validGameName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByGameAndSystem - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByGameAndSystem(validGameName, validSystemName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByGameAndRegion - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByGameAndRegion(validGameName, validRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllBySystemAndRegion - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllBySystemAndRegion(validSystemName, validRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByCategoryAndGameAndSystem - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByCategoryAndGameAndSystem(validCategory, validGameName, validSystemName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByCategoryAndGameAndRegion - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByCategoryAndGameAndRegion(validCategory, validGameName, validRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid parameters - when findAllByGameAndSystemAndRegion - then return valid speedruns`() {
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByGameAndSystemAndRegion(validGameName, validSystemName, validRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given valid parameters - when find - then return valid speedrun`() {
            val expectedSpeedrun:Speedrun = validSpeedrun

            val actualSpeedrun: Speedrun = speedrunSeekService.find(validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualSpeedrun shouldEqual expectedSpeedrun
        }

        @Test
        fun `given valid parameters - when findAll - then return valid speedruns`() {
            // ? Think of method to allow filtering on mock
            whenever(speedrunRepository.findAllByRemovedOnIsNull()).thenReturn(validSpeedruns)
            val expectedSpeedruns: List<Speedrun> = validSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAll(validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid game name - when findAllByGame - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByGame(invalidGameName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid system name - when findAllBySystem - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllBySystem(invalidSystemName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid region - when findAllByRegion - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByRegion(invalidRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByCategoryAndGame - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByCategoryAndGame(invalidCategory, invalidGameName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByGameAndSystem - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByGameAndSystem(invalidGameName, invalidSystemName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByGameAndRegion - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByGameAndRegion(invalidGameName, invalidRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllBySystemAndRegion - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllBySystemAndRegion(invalidSystemName, invalidRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByCategoryAndGameAndSystem - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByCategoryAndGameAndSystem(invalidCategory, invalidGameName, invalidSystemName)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByCategoryAndGameAndRegion - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByCategoryAndGameAndRegion(invalidCategory, invalidGameName, invalidRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid parameters - when findAllByGameAndSystemAndRegion - then return no speedruns`() {
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAllByGameAndSystemAndRegion(invalidGameName, invalidSystemName, invalidRegion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

        @Test
        fun `given invalid parameters - when find - then return invalid speedrun`() {
            invoking {
                speedrunSeekService.find(invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)
            } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAll - then return no speedruns`() {
            whenever(speedrunRepository.findAllByRemovedOnIsNull()).thenReturn(noSpeedruns)
            val expectedSpeedruns: List<Speedrun> = noSpeedruns

            val actualSpeedruns: List<Speedrun> = speedrunSeekService.findAll(invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)

            actualSpeedruns shouldEqual expectedSpeedruns
        }

    }

}
