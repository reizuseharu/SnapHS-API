package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.Cart as CartInput
import com.reizu.snaphs.api.dto.output.Cart as CartOutput
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController {

    @Autowired
    private lateinit var cartService: CartService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody cartInput: CartInput): CartOutput {
        return cartService.create(cartInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<CartOutput> {
        return cartService.findAll(search)
    }

    @GetMapping(path = ["/deepSearch"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("game")
        gameName: String?,
        @RequestParam("system")
        systemName: String?,
        @RequestParam("isEmulated")
        isEmulated: Boolean?,
        @RequestParam("region")
        region: Region?,
        @RequestParam("version")
        version: String?
    ): Iterable<CartOutput> {
        return cartService.findAll(gameName, systemName, isEmulated, region, version)
    }

}
