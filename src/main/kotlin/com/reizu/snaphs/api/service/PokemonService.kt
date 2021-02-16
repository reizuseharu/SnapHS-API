package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.Pokemon
import com.reizu.snaphs.api.service.seek.PokemonSeekService
import com.reizu.snaphs.api.dto.input.Pokemon as PokemonInput
import com.reizu.snaphs.api.dto.output.Pokemon as PokemonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PokemonService {

    @Autowired
    private lateinit var pokemonSeekService: PokemonSeekService

    fun create(pokemonInput: PokemonInput): PokemonOutput {
        return pokemonInput.run {
            val pokemon = Pokemon(
                name = name,
                dexNumber = dexNumber
            )
            val createdPokemon: Pokemon = pokemonSeekService.create(pokemon)

            createdPokemon.output
        }
    }

    fun findAll(search: String?): Iterable<PokemonOutput> {
        return pokemonSeekService.findAllActive(search = search).map { pokemon -> pokemon.output }
    }

    fun findByName(name: String): PokemonOutput {
        return pokemonSeekService.findByName(name).output
    }

    fun findByDexNumber(dexNumber: String): PokemonOutput {
        return pokemonSeekService.findByDexNumber(dexNumber).output
    }

}
