package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.RaceTime as RaceTimeInput
import com.reizu.snaphs.api.dto.output.RaceTime as RaceTimeOutput
import com.reizu.snaphs.api.dto.update.RaceTime as RaceTimeRegister
import com.reizu.snaphs.api.service.RaceTimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/raceTime")
class RaceTimeController {

    @Autowired
    private lateinit var raceTimeService: RaceTimeService

    @PostMapping(path = ["/register"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody raceTimeInput: RaceTimeInput): RaceTimeOutput {
        return raceTimeService.register(raceTimeInput)
    }

    @PatchMapping(path = ["/verifyTime"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerTime(@RequestBody raceTimeRegister: RaceTimeRegister): RaceTimeOutput {
        return raceTimeService.registerTime(raceTimeRegister)
    }

}
