package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.dto.input.Cart as CartInput
import com.reizu.snaphs.api.dto.output.Cart as CartOutput
import com.reizu.snaphs.api.service.seek.CartSeekService
import com.reizu.snaphs.api.service.seek.GameSeekService
import com.reizu.snaphs.api.service.seek.SystemSeekService
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
internal class CartServiceTest {

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

    private val validSearch: String = "gameName:$validGameName"
    private val invalidSearch: String = "gameName:$invalidGameName"

    private val validGame: Game = EC.VALID_GAME
    private val invalidGame: Game = EC.INVALID_GAME

    private val validSystem: System = EC.VALID_SYSTEM
    private val invalidSystem: System = EC.INVALID_SYSTEM

    private val validCartInput: CartInput = EC.VALID_CART_INPUT
    private val invalidCartInput: CartInput = EC.INVALID_CART_INPUT

    private val validCart: Cart = EC.VALID_CART
    private val invalidCart: Cart = EC.INVALID_CART

    private val validCarts: List<Cart> = listOf(validCart)
    private val validCartOutputs: List<CartOutput> = validCarts.map { system -> system.output }

    private val noCarts: List<Cart> = emptyList()
    private val noCartOutputs: List<CartOutput> = emptyList()

    @Mock
    private lateinit var gameSeekService: GameSeekService

    @Mock
    private lateinit var systemSeekService: SystemSeekService

    @Mock
    private lateinit var cartSeekService: CartSeekService

    @InjectMocks
    private lateinit var cartService: CartService

    @BeforeAll
    fun setUp() {
        whenever(gameSeekService.findByName(validGameName)).thenReturn(validGame)
        whenever(systemSeekService.findByName(validSystemName)).thenReturn(validSystem)
        whenever(cartSeekService.find(validGameName, validSystemName, validIsEmulated, validRegion, validVersion)).thenReturn(validCart)
        whenever(cartSeekService.create(validCart)).thenReturn(validCart)
        whenever(cartSeekService.findAllActive(search = validSearch)).thenReturn(validCarts)
        whenever(cartSeekService.findAll(validGameName, validSystemName, validIsEmulated, validRegion, validVersion)).thenReturn(validCarts)

        whenever(gameSeekService.findByName(invalidGameName)).thenReturn(invalidGame)
        whenever(systemSeekService.findByName(invalidSystemName)).thenReturn(invalidSystem)
        whenever(cartSeekService.find(invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)).thenReturn(invalidCart)
        doThrow(IllegalArgumentException::class).whenever(cartSeekService).create(invalidCart)
        whenever(cartSeekService.findAllActive(search = invalidSearch)).thenReturn(noCarts)
        whenever(cartSeekService.findAll(invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)).thenReturn(noCarts)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid cartInput - when create - then return valid cartOutput`() {
            val expectedCartOutput: CartOutput = validCart.output

            val actualCartOutput: CartOutput = cartService.create(validCartInput)

            actualCartOutput shouldEqual expectedCartOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid cartOutputs`() {
            val expectedCartOutputs: Iterable<CartOutput> = validCartOutputs

            val actualCartOutputs: Iterable<CartOutput> = cartService.findAll(validSearch)

            actualCartOutputs shouldEqual expectedCartOutputs
        }

        @Test
        fun `given valid parameters - when findAll - then return valid cartOutputs`() {
            val expectedCartOutputs: Iterable<CartOutput> = validCartOutputs

            val actualCartOutputs: Iterable<CartOutput> = cartService.findAll(validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualCartOutputs shouldEqual expectedCartOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid cartInput - when create - then throw IllegalArgumentException`() {
            invoking { cartService.create(invalidCartInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no cartOutputs`() {
            val expectedCartOutputs: Iterable<CartOutput> = noCartOutputs

            val actualCartOutputs: Iterable<CartOutput> = cartService.findAll(invalidSearch)

            actualCartOutputs shouldEqual expectedCartOutputs
        }

        @Test
        fun `given invalid parameters - when findAll - then return no cartOutputs`() {
            val expectedCartOutputs: Iterable<CartOutput> = noCartOutputs

            val actualCartOutputs: Iterable<CartOutput> = cartService.findAll(invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)

            actualCartOutputs shouldEqual expectedCartOutputs
        }

    }
}
