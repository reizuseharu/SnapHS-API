package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.input.Cart as CartInput
import com.reizu.snaphs.api.dto.output.Cart as CartOutput
import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.service.seek.CartSeekService
import com.reizu.snaphs.api.service.seek.GameSeekService
import com.reizu.snaphs.api.service.seek.SystemSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CartService {

    @Autowired
    private lateinit var cartSeekService: CartSeekService

    @Autowired
    private lateinit var gameSeekService: GameSeekService

    @Autowired
    private lateinit var systemSeekService: SystemSeekService

    fun create(cartInput: CartInput): CartOutput {
        return cartInput.run {
            val game: Game = gameSeekService.findByName(gameName)
            val system: System = systemSeekService.findByName(systemName)

            val cart = Cart(
                game = game,
                system = system,
                region = region,
                version = version
            )
            val createdCart: Cart = cartSeekService.create(cart)

            createdCart.output
        }
    }

    fun findAll(
        search: String?
    ): Iterable<CartOutput> {
        return cartSeekService.findAllActive(search = search).map { cart -> cart.output }
    }

    fun findAll(
        gameName: String?,
        systemName: String?,
        isEmulated: Boolean?,
        region: Region?,
        version: String?
    ): Iterable<CartOutput> {
        return cartSeekService
            .findAll(gameName, systemName, isEmulated, region, version)
            .map { cart -> cart.output }
    }

}
