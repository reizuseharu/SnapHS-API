package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.Challenge
import com.reizu.snaphs.api.entity.Pokemon
import com.reizu.snaphs.api.entity.Stage
import com.reizu.snaphs.api.service.seek.ChallengeSeekService
import com.reizu.snaphs.api.service.seek.PokemonSeekService
import com.reizu.snaphs.api.dto.input.Challenge as ChallengeInput
import com.reizu.snaphs.api.dto.output.Challenge as ChallengeOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChallengeService {

    @Autowired
    private lateinit var challengeSeekService: ChallengeSeekService

    @Autowired
    private lateinit var pokemonSeekService: PokemonSeekService

    fun create(challengeInput: ChallengeInput): ChallengeOutput {
        return challengeInput.run {
            val pokemon: Pokemon? = pokemonName?.let { pokemonSeekService.findByName(pokemonName) }

            val challenge = Challenge(
                name = name,
                pokemon = pokemon,
                stage = stage
            )
            val createdChallenge: Challenge = challengeSeekService.create(challenge)

            createdChallenge.output
        }
    }

    fun findAll(search: String?): Iterable<ChallengeOutput> {
        return challengeSeekService.findAllActive(search = search).map { challenge -> challenge.output }
    }

    fun findByName(name: String): ChallengeOutput {
        return challengeSeekService.findByName(name).output
    }

    fun findAllByPokemonName(pokemonName: String): Iterable<ChallengeOutput> {
        return challengeSeekService.findAllByPokemonName(pokemonName)
            .map { challenge -> challenge.output }
    }

    fun findAllByPokemonDexNumber(pokemonDexNumber: String): Iterable<ChallengeOutput> {
        return challengeSeekService.findAllByPokemonDexNumber(pokemonDexNumber)
            .map { challenge -> challenge.output }
    }

    fun findAllByStage(stage: Stage): Iterable<ChallengeOutput> {
        return challengeSeekService.findAllByStage(stage)
            .map { challenge -> challenge.output }
    }

}
