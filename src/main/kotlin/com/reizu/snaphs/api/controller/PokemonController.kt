package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.Pokemon as PokemonInput
import com.reizu.snaphs.api.dto.output.Pokemon as PokemonOutput
import com.reizu.snaphs.api.service.PokemonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pokemon")
class PokemonController {

    @Autowired
    private lateinit var pokemonService: PokemonService

    @CrossOrigin
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody pokemonInput: PokemonInput): PokemonOutput {
        return pokemonService.create(pokemonInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<PokemonOutput> {
        return pokemonService.findAll(search = search)
    }

    @GetMapping(path = ["/name/{name}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByName(
        @PathVariable("name")
        name: String
    ): PokemonOutput {
        return pokemonService.findByName(name)
    }

    @GetMapping(path = ["/dexNumber/{dexNumber}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByDexNumber(
        @PathVariable("dexNumber")
        dexNumber: String
    ): PokemonOutput {
        return pokemonService.findByDexNumber(dexNumber)
    }

}
