package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.entity.Stage
import com.reizu.snaphs.api.dto.input.Challenge as ChallengeInput
import com.reizu.snaphs.api.dto.output.Challenge as ChallengeOutput
import com.reizu.snaphs.api.service.ChallengeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/challenge")
class ChallengeController {

    @Autowired
    private lateinit var challengeService: ChallengeService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody challengeInput: ChallengeInput): ChallengeOutput {
        return challengeService.create(challengeInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<ChallengeOutput> {
        return challengeService.findAll(search = search)
    }

    @GetMapping(path = ["/name/{name}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByName(
        @PathVariable("name")
        name: String
    ): ChallengeOutput {
        return challengeService.findByName(name)
    }

    @GetMapping(path = ["/pokemon/{pokemonName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByPokemon(
        @PathVariable("pokemonName")
        pokemonName: String
    ): Iterable<ChallengeOutput> {
        return challengeService.findAllByPokemonName(pokemonName)
    }

    @GetMapping(path = ["/dexNumber/{dexNumber}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByDexNumber(
        @PathVariable("dexNumber")
        dexNumber: String
    ): Iterable<ChallengeOutput> {
        return challengeService.findAllByPokemonDexNumber(dexNumber)
    }

    @GetMapping(path = ["/stage/{stage}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByStage(
        @PathVariable("stage")
        stage: Stage
    ): Iterable<ChallengeOutput> {
        return challengeService.findAllByStage(stage)
    }

}
