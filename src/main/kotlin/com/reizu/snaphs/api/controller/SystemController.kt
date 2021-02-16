package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.System as SystemInput
import com.reizu.snaphs.api.dto.output.System as SystemOutput
import com.reizu.snaphs.api.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/system")
class SystemController {

    @Autowired
    private lateinit var systemService: SystemService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody systemInput: SystemInput): SystemOutput {
        return systemService.create(systemInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<SystemOutput> {
        return systemService.findAll(search = search)
    }

}
