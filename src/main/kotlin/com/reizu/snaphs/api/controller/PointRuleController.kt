package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.LeaguePointRule as LeaguePointRuleInput
import com.reizu.snaphs.api.dto.output.PointRule as PointRuleOutput
import com.reizu.snaphs.api.service.PointService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pointRule")
class PointRuleController {

    @Autowired
    private lateinit var pointService: PointService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leaguePointRuleInput: LeaguePointRuleInput): List<PointRuleOutput> {
        return pointService.create(leaguePointRuleInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<PointRuleOutput> {
        return pointService.findAll(search = search)
    }

    @GetMapping(path = ["/league/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<PointRuleOutput> {
        return pointService.findAll(leagueName, season, tierLevel)
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leaguePointRuleInput: LeaguePointRuleInput): List<PointRuleOutput> {
        return pointService.replace(leaguePointRuleInput)
    }

}
