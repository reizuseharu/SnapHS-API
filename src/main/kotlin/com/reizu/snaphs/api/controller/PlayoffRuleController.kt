package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.LeaguePlayoffRule as LeaguePlayoffRuleInput
import com.reizu.snaphs.api.dto.output.PlayoffRule as PlayoffRuleOutput
import com.reizu.snaphs.api.service.PlayoffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/playoffRule")
class PlayoffRuleController {

    @Autowired
    private lateinit var playoffService: PlayoffService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leaguePlayoffRuleInput: LeaguePlayoffRuleInput): List<PlayoffRuleOutput> {
        return playoffService.create(leaguePlayoffRuleInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<PlayoffRuleOutput> {
        return playoffService.findAll(search = search)
    }

    @GetMapping(path = ["/league/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<PlayoffRuleOutput> {
        return playoffService.findAll(leagueName, season, tierLevel)
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leaguePlayoffRuleInput: LeaguePlayoffRuleInput): List<PlayoffRuleOutput> {
        return playoffService.replace(leaguePlayoffRuleInput)
    }

}
