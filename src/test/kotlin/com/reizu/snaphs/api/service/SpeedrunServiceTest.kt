package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.service.seek.CartSeekService
import com.reizu.snaphs.api.dto.input.Speedrun as SpeedrunInput
import com.reizu.snaphs.api.dto.output.Speedrun as SpeedrunOutput
import com.reizu.snaphs.api.service.seek.SpeedrunSeekService
import com.reizu.snaphs.api.service.TestConstants as C
import com.reizu.snaphs.api.service.TestEntityConstants as EC
import com.nhaarman.mockitokotlin2.doThrow
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
import java.lang.IllegalArgumentException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SpeedrunServiceTest {

    private val validCategory: String = C.VALID_CATEGORY
    private val invalidCategory: String = C.INVALID_CATEGORY

    private val validGameName: String = C.VALID_GAME_NAME
    private val invalidGameName: String = C.INVALID_GAME_NAME

    private val validSystemName: String = C.VALID_SYSTEM_NAME
    private val invalidSystemName: String = C.INVALID_SYSTEM_NAME

    private val validIsEmulated: Boolean = C.VALID_IS_EMULATED
    private val invalidIsEmulated: Boolean = C.INVALID_IS_EMULATED

    private val validRegion: Region = C.VALID_REGION
    private val invalidRegion: Region = C.INVALID_REGION

    private val validVersion: String = C.VALID_VERSION
    private val invalidVersion: String = C.INVALID_VERSION

    private val validSearch: String = "category:$validCategory"
    private val invalidSearch: String = "category:$invalidCategory"

    private val validSpeedrunInput: SpeedrunInput = EC.VALID_SPEEDRUN_INPUT
    private val invalidSpeedrunInput: SpeedrunInput = EC.INVALID_SPEEDRUN_INPUT

    private val validCart: Cart = EC.VALID_CART
    private val invalidCart: Cart = EC.INVALID_CART

    private val validSpeedrun: Speedrun = EC.VALID_SPEEDRUN
    private val invalidSpeedrun: Speedrun = EC.INVALID_SPEEDRUN

    private val validSpeedruns: List<Speedrun> = listOf(validSpeedrun)
    private val validSpeedrunOutputs: List<SpeedrunOutput> = validSpeedruns.map { system -> system.output }

    private val noSpeedruns: List<Speedrun> = emptyList()
    private val noSpeedrunOutputs: List<SpeedrunOutput> = emptyList()

    @Mock
    private lateinit var speedrunSeekService: SpeedrunSeekService

    @Mock
    private lateinit var cartSeekService: CartSeekService

    @InjectMocks
    private lateinit var speedrunService: SpeedrunService

    @BeforeAll
    fun setUp() {
        whenever(cartSeekService.find(validGameName, validSystemName, validIsEmulated, validRegion, validVersion)).thenReturn(validCart)
        whenever(speedrunSeekService.find(validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)).thenReturn(validSpeedrun)
        whenever(speedrunSeekService.create(validSpeedrun)).thenReturn(validSpeedrun)
        whenever(speedrunSeekService.findAllActive(search = validSearch)).thenReturn(validSpeedruns)
        whenever(speedrunSeekService.findAll(validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)).thenReturn(validSpeedruns)

        whenever(cartSeekService.find(invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)).thenReturn(invalidCart)
        whenever(speedrunSeekService.find(invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)).thenReturn(invalidSpeedrun)
        doThrow(IllegalArgumentException::class).whenever(speedrunSeekService).create(invalidSpeedrun)
        whenever(speedrunSeekService.findAllActive(search = invalidSearch)).thenReturn(noSpeedruns)
        whenever(speedrunSeekService.findAll(invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)).thenReturn(noSpeedruns)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid speedrunInput - when create - then return valid speedrunOutput`() {
            val expectedSpeedrunOutput: SpeedrunOutput = validSpeedrun.output

            val actualSpeedrunOutput: SpeedrunOutput = speedrunService.create(validSpeedrunInput)

            actualSpeedrunOutput shouldEqual expectedSpeedrunOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid speedrunOutputs`() {
            val expectedSpeedrunOutputs: Iterable<SpeedrunOutput> = validSpeedrunOutputs

            val actualSpeedrunOutputs: Iterable<SpeedrunOutput> = speedrunService.findAll(validSearch)

            actualSpeedrunOutputs shouldEqual expectedSpeedrunOutputs
        }

        @Test
        fun `given valid parameters - when findAll - then return valid speedrunOutputs`() {
            val expectedSpeedrunOutputs: Iterable<SpeedrunOutput> = validSpeedrunOutputs

            val actualSpeedrunOutputs: Iterable<SpeedrunOutput> = speedrunService.findAll(validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualSpeedrunOutputs shouldEqual expectedSpeedrunOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid speedrunInput - when create - then throw IllegalArgumentException`() {
            invoking { speedrunService.create(invalidSpeedrunInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no speedrunOutputs`() {
            val expectedSpeedrunOutputs: Iterable<SpeedrunOutput> = noSpeedrunOutputs

            val actualSpeedrunOutputs: Iterable<SpeedrunOutput> = speedrunService.findAll(invalidSearch)

            actualSpeedrunOutputs shouldEqual expectedSpeedrunOutputs
        }

        @Test
        fun `given invalid parameters - when findAll - then return no speedrunOutputs`() {
            val expectedSpeedrunOutputs: Iterable<SpeedrunOutput> = noSpeedrunOutputs

            val actualSpeedrunOutputs: Iterable<SpeedrunOutput> = speedrunService.findAll(invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)

            actualSpeedrunOutputs shouldEqual expectedSpeedrunOutputs
        }

    }
}
