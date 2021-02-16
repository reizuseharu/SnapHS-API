package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.output.Race as RaceOutput
import com.reizu.snaphs.api.service.OpenRaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/openRace")
class OpenRaceController {

    @Autowired
    private lateinit var openRaceService: OpenRaceService

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @RequestParam("startedOn")
        startedOn: LocalDateTime?
    ): List<RaceOutput> {
        return openRaceService.findOpenRaces(startedOn ?: LocalDateTime.now())
    }

    @GetMapping(path = ["/isRaceCompleted/{race}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("race")
        raceName: String
    ): Boolean {
        return openRaceService.isRaceOpen(raceName)
    }

}
