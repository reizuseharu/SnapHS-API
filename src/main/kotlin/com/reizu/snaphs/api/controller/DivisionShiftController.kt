package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.LeagueDivisionShiftRule as LeagueDivisionShiftRuleInput
import com.reizu.snaphs.api.dto.output.DivisionShiftRule as DivisionShiftRuleOutput
import com.reizu.snaphs.api.service.DivisionShiftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/divisionShiftRule")
class DivisionShiftController {

    @Autowired
    private lateinit var divisionShiftService: DivisionShiftService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.create(leagueDivisionShiftRuleInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<DivisionShiftRuleOutput> {
        return divisionShiftService.findAll(search = search)
    }

    @GetMapping(path = ["/divisionShift/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<DivisionShiftRuleOutput> {
        return divisionShiftService.findAll(leagueName, season, tierLevel)
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.replace(leagueDivisionShiftRuleInput)
    }

    @PostMapping(path = ["/promotion"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPromotionRules(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.createPromotionRules(leagueDivisionShiftRuleInput)
    }

    @GetMapping(path = ["/promotion/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllPromotionRules(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<DivisionShiftRuleOutput> {
        return divisionShiftService.findAllPromotionRules(leagueName, season, tierLevel)
    }

    @PutMapping(path = ["/promotion"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replacePromotionRules(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.replacePromotionRules(leagueDivisionShiftRuleInput)
    }

    @PostMapping(path = ["/relegation"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createRelegationRules(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.createRelegationRules(leagueDivisionShiftRuleInput)
    }

    @GetMapping(path = ["/relegation/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllRelegationRules(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<DivisionShiftRuleOutput> {
        return divisionShiftService.findAllRelegationRules(leagueName, season, tierLevel)
    }

    @PutMapping(path = ["/relegation"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replaceRelegationRules(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.replaceRelegationRules(leagueDivisionShiftRuleInput)
    }

}
