package com.reizu.snaphs.api.service.seek

import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.Cart
import com.reizu.snaphs.api.repository.CartRepository
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
internal class CartSeekServiceTest {

    private val validGameName: String = C.VALID_GAME_NAME
    private val invalidGameName: String = C.INVALID_GAME_NAME

    private val validSystemName: String = C.VALID_SYSTEM_NAME
    private val invalidSystemName: String = C.INVALID_SYSTEM_NAME

    private val validRegion: Region = C.VALID_REGION
    private val invalidRegion: Region = C.INVALID_REGION

    private val validIsEmulated: Boolean = C.VALID_IS_EMULATED
    private val invalidIsEmulated: Boolean = C.INVALID_IS_EMULATED

    private val validVersion: String = C.VALID_VERSION
    private val invalidVersion: String = C.INVALID_VERSION

    private val noCarts: List<Cart> = emptyList()

    @Mock
    private lateinit var validCart: Cart

    @Mock
    private lateinit var validCarts: List<Cart>

    @Mock
    private lateinit var cartRepository: CartRepository

    @InjectMocks
    private lateinit var cartSeekService: CartSeekService

    @BeforeAll
    fun setUp() {
        whenever(cartRepository.findAllByGameNameAndRemovedOnIsNull(validGameName)).thenReturn(validCarts)
        whenever(cartRepository.findAllBySystemNameAndRemovedOnIsNull(validSystemName)).thenReturn(validCarts)
        whenever(cartRepository.findAllByRegionAndRemovedOnIsNull(validRegion)).thenReturn(validCarts)
        whenever(cartRepository.findAllByGameNameAndSystemNameAndRemovedOnIsNull(validGameName, validSystemName)).thenReturn(validCarts)
        whenever(cartRepository.findAllByGameNameAndRegionAndRemovedOnIsNull(validGameName, validRegion)).thenReturn(validCarts)
        whenever(cartRepository.findAllBySystemNameAndRegionAndRemovedOnIsNull(validSystemName, validRegion)).thenReturn(validCarts)
        whenever(cartRepository
            .findFirstByGameNameAndSystemNameAndSystemEmulatedAndRegionAndVersionAndRemovedOnIsNull(validGameName, validSystemName, validIsEmulated, validRegion, validVersion)
        ).thenReturn(validCart)

        whenever(cartRepository.findAllByGameNameAndRemovedOnIsNull(invalidGameName)).thenReturn(noCarts)
        whenever(cartRepository.findAllBySystemNameAndRemovedOnIsNull(invalidSystemName)).thenReturn(noCarts)
        whenever(cartRepository.findAllByRegionAndRemovedOnIsNull(invalidRegion)).thenReturn(noCarts)
        whenever(cartRepository.findAllByGameNameAndSystemNameAndRemovedOnIsNull(invalidGameName, invalidSystemName)).thenReturn(noCarts)
        whenever(cartRepository.findAllByGameNameAndRegionAndRemovedOnIsNull(invalidGameName, invalidRegion)).thenReturn(noCarts)
        whenever(cartRepository.findAllBySystemNameAndRegionAndRemovedOnIsNull(invalidSystemName, invalidRegion)).thenReturn(noCarts)
        whenever(cartRepository
            .findFirstByGameNameAndSystemNameAndSystemEmulatedAndRegionAndVersionAndRemovedOnIsNull(invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)
        ).thenReturn(null)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid game name - when findAllByGame - then return valid carts`() {
            val expectedCarts: List<Cart> = validCarts

            val actualCarts: List<Cart> = cartSeekService.findAllByGame(validGameName)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given valid system name - when findAllBySystem - then return valid carts`() {
            val expectedCarts: List<Cart> = validCarts

            val actualCarts: List<Cart> = cartSeekService.findAllBySystem(validSystemName)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given valid region - when findAllByRegion - then return valid carts`() {
            val expectedCarts: List<Cart> = validCarts

            val actualCarts: List<Cart> = cartSeekService.findAllByRegion(validRegion)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given valid parameters - when findAllByGameAndSystem - then return valid carts`() {
            val expectedCarts: List<Cart> = validCarts

            val actualCarts: List<Cart> = cartSeekService.findAllByGameAndSystem(validGameName, validSystemName)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given valid parameters - when findAllByGameAndRegion - then return valid carts`() {
            val expectedCarts: List<Cart> = validCarts

            val actualCarts: List<Cart> = cartSeekService.findAllByGameAndRegion(validGameName, validRegion)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given valid parameters - when findAllBySystemAndRegion - then return valid carts`() {
            val expectedCarts: List<Cart> = validCarts

            val actualCarts: List<Cart> = cartSeekService.findAllBySystemAndRegion(validSystemName, validRegion)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given valid parameters - when find - then return valid cart`() {
            val expectedCart:Cart = validCart

            val actualCart: Cart = cartSeekService.find(validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualCart shouldEqual expectedCart
        }

        @Test
        fun `given valid parameters - when findAll - then return valid carts`() {
            // ? Think of method to allow filtering on mock
            whenever(cartRepository.findAllByRemovedOnIsNull()).thenReturn(validCarts)
            val expectedCarts: List<Cart> = validCarts

            val actualCarts: List<Cart> = cartSeekService.findAll(validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualCarts shouldEqual expectedCarts
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid game name - when findAllByGame - then return no carts`() {
            val expectedCarts: List<Cart> = noCarts

            val actualCarts: List<Cart> = cartSeekService.findAllByGame(invalidGameName)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given invalid system name - when findAllBySystem - then return no carts`() {
            val expectedCarts: List<Cart> = noCarts

            val actualCarts: List<Cart> = cartSeekService.findAllBySystem(invalidSystemName)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given invalid region - when findAllByRegion - then return no carts`() {
            val expectedCarts: List<Cart> = noCarts

            val actualCarts: List<Cart> = cartSeekService.findAllByRegion(invalidRegion)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given invalid parameters - when findAllByGameAndSystem - then return no carts`() {
            val expectedCarts: List<Cart> = noCarts

            val actualCarts: List<Cart> = cartSeekService.findAllByGameAndSystem(invalidGameName, invalidSystemName)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given invalid parameters - when findAllByGameAndRegion - then return no carts`() {
            val expectedCarts: List<Cart> = noCarts

            val actualCarts: List<Cart> = cartSeekService.findAllByGameAndRegion(invalidGameName, invalidRegion)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given invalid parameters - when findAllBySystemAndRegion - then return no carts`() {
            val expectedCarts: List<Cart> = noCarts

            val actualCarts: List<Cart> = cartSeekService.findAllBySystemAndRegion(invalidSystemName, invalidRegion)

            actualCarts shouldEqual expectedCarts
        }

        @Test
        fun `given invalid parameters - when find - then return invalid cart`() {
            invoking {
                cartSeekService.find(invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)
            } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAll - then return no carts`() {
            whenever(cartRepository.findAllByRemovedOnIsNull()).thenReturn(noCarts)
            val expectedCarts: List<Cart> = noCarts

            val actualCarts: List<Cart> = cartSeekService.findAll(invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)

            actualCarts shouldEqual expectedCarts
        }

    }

}
