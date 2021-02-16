package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.EndSeason
import com.reizu.snaphs.api.dto.input.LowerTier
import com.reizu.snaphs.api.dto.input.StartSeason
import com.reizu.snaphs.api.dto.output.LeagueSchedule
import com.reizu.snaphs.api.dto.search.League as LeagueSearch
import com.reizu.snaphs.api.dto.update.LeagueDivisionShift as LeagueDivisionShiftUpdate
import com.reizu.snaphs.api.dto.input.League as LeagueInput
import com.reizu.snaphs.api.dto.output.League as LeagueOutput
import com.reizu.snaphs.api.service.LeagueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/league")
class LeagueController {

    @Autowired
    private lateinit var leagueService: LeagueService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leagueInput: LeagueInput): LeagueOutput {
        return leagueService.create(leagueInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<LeagueOutput> {
        return leagueService.findAll(search = search)
    }

    @PostMapping(path = ["/endSeason"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun endSeason(@RequestBody endSeason: EndSeason): LeagueOutput {
        return leagueService.endSeason(endSeason)
    }

    @PostMapping(path = ["/startNewSeason"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun startNewSeason(@RequestBody startSeason: StartSeason): List<LeagueOutput> {
        return leagueService.startNewSeason(startSeason)
    }

    @PostMapping(path = ["/addLowerTier"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addLowerTier(@RequestBody lowerTier: LowerTier): LeagueOutput {
        return leagueService.addLowerTier(lowerTier)
    }

    @PatchMapping(path = ["/divisionShifts"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun modifyDivisionShifts(@RequestBody leagueDivisionShift: LeagueDivisionShiftUpdate): LeagueOutput {
        return leagueService.modifyDivisionShifts(leagueDivisionShift)
    }

    @PostMapping(path = ["/generateRoundRobin"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun generateRoundRobin(@RequestBody leagueSearch: LeagueSearch): LeagueSchedule {
        return leagueService.generateRoundRobin(leagueSearch)
    }

}
