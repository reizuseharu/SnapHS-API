package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.Runner as RunnerInput
import com.reizu.snaphs.api.dto.input.LeagueRunner as LeagueRunnerInput
import com.reizu.snaphs.api.dto.output.Runner as RunnerOutput
import com.reizu.snaphs.api.dto.output.LeagueRunner as LeagueRunnerOutput
import com.reizu.snaphs.api.service.RunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/runner")
class RunnerController {

    @Autowired
    private lateinit var runnerService: RunnerService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody runnerInput: RunnerInput): RunnerOutput {
        return runnerService.create(runnerInput)
    }

    @PostMapping(path = ["/registerToLeague"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody leagueRunnerInput: LeagueRunnerInput): LeagueRunnerOutput {
        return runnerService.register(leagueRunnerInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<RunnerOutput> {
        return runnerService.findAll(search = search)
    }

    @GetMapping(path = ["/race/{race}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllRunners(
        @PathVariable("race")
        raceName: String
    ): List<RunnerOutput> {
        return runnerService.findAllRunners(raceName)
    }

    @GetMapping(path = ["/league/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllRunners(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<RunnerOutput> {
        return runnerService.findAllRunners(leagueName, season, tierLevel)
    }

}
