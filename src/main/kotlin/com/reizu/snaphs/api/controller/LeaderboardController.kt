package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.dto.input.ScoreAttack as ScoreAttackInput
import com.reizu.snaphs.api.dto.output.ScoreAttack as ScoreAttackOutput
import com.reizu.snaphs.api.service.ScoreAttackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController {

    @Autowired
    private lateinit var scoreAttackService: ScoreAttackService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun submitScore(@RequestBody scoreAttackInput: ScoreAttackInput): ScoreAttackOutput {
        return scoreAttackService.create(scoreAttackInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun viewLeaderboard(): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllScoreOrdered()
    }

    @GetMapping(path = ["/pokemon/{pokemonName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun viewLeaderboardByPokemon(
        @PathVariable("pokemonName")
        pokemonName: String
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllScoreByPokemon(pokemonName)
    }

    @GetMapping(path = ["/challenge/{challengeName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun viewLeaderboardByChallenge(
        @PathVariable("challengeName")
        challengeName: String
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllScoreByChallenge(challengeName)
    }

    @GetMapping(path = ["/region/{region}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun viewLeaderboardByRegion(
        @PathVariable("region")
        region: Region
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllScoreByRegion(region)
    }

    @GetMapping(path = ["/console/{console}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun viewLeaderboardByConsole(
        @PathVariable("console")
        console: Console
    ): Iterable<ScoreAttackOutput> {
        return scoreAttackService.findAllScoreByConsole(console)
    }

}
