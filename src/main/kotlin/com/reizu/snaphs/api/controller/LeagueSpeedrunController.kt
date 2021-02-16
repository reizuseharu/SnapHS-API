package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.entity.LeagueType
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.service.LeagueSpeedrunService
import com.reizu.snaphs.api.dto.input.LeagueSpeedrun as LeagueSpeedrunInput
import com.reizu.snaphs.api.dto.output.LeagueSpeedrun as LeagueSpeedrunOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Future

@RestController
@RequestMapping("/leagueSpeedrun")
class LeagueSpeedrunController {

    @Autowired
    private lateinit var leagueSpeedrunService: LeagueSpeedrunService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leagueSpeedrunInput: LeagueSpeedrunInput): LeagueSpeedrunOutput {
        return leagueSpeedrunService.create(leagueSpeedrunInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunService.findAll(search = search)
    }

    @GetMapping(path = ["/deepSearch"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("league")
        leagueName: String?,
        @RequestParam("type")
        leagueType: LeagueType?,
        @RequestParam("category")
        category: String?,
        @RequestParam("game")
        gameName: String?,
        @RequestParam("system")
        systemName: String?,
        @RequestParam("isEmulated")
        isEmulated: Boolean?,
        @RequestParam("region")
        region: Region?,
        @RequestParam("version")
        version: String?
    ): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunService
            .findAll(leagueName, leagueType, category, gameName, systemName, isEmulated, region, version)
    }

}
