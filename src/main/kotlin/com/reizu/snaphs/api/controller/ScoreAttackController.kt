package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.dto.input.ScoreAttack as ScoreAttackInput
import com.reizu.snaphs.api.dto.output.ScoreAttack as ScoreAttackOutput
import com.reizu.snaphs.api.dto.update.ScoreAttack as ScoreAttackUpdate
import com.reizu.snaphs.api.service.ScoreAttackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/scoreAttack")
class ScoreAttackController {

    @Autowired
    private lateinit var scoreAttackService: ScoreAttackService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody scoreAttackInput: ScoreAttackInput): ScoreAttackOutput {
        return scoreAttackService.create(scoreAttackInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAll(search = search)
    }

    @GetMapping(path = ["/user/{userName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByUser(
        @PathVariable("userName")
        userName: String
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllByUser(userName)
    }

    @GetMapping(path = ["/challenge/{challengeName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByChallenge(
        @PathVariable("challengeName")
        challengeName: String
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllByChallenge(challengeName)
    }

    @GetMapping(path = ["/pokemon/{pokemonName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByPokemon(
        @PathVariable("pokemonName")
        pokemonName: String
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllByPokemon(pokemonName)
    }

    @GetMapping(path = ["/console/{console}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByConsole(
        @PathVariable("console")
        console: Console
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllByConsole(console)
    }

    @GetMapping(path = ["/region/{region}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByRegion(
        @PathVariable("region")
        region: Region
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllByRegion(region)
    }

    @GetMapping(path = ["/challenge/{challengeName}/console/{console}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByChallengeAndConsole(
        @PathVariable("challengeName")
        challengeName: String,
        @PathVariable("console")
        console: Console
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllByChallengeAndConsole(challengeName, console)
    }

    @GetMapping(path = ["/ordered"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllOrdered(): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllOrdered()
    }

    @PutMapping(path = ["/validate"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun validateScoreAttack(@RequestBody scoreAttackUpdate: ScoreAttackUpdate): ScoreAttackOutput {
        return scoreAttackService.validateScoreAttack(scoreAttackUpdate)
    }

}
