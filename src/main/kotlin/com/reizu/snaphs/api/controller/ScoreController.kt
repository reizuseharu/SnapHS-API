package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.Score as ScoreInput
import com.reizu.snaphs.api.dto.output.Score as ScoreOutput
import com.reizu.snaphs.api.service.ScoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/score")
class ScoreController {

    @Autowired
    private lateinit var scoreService: ScoreService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody scoreInput: ScoreInput): ScoreOutput {
        return scoreService.create(scoreInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<ScoreOutput> {
        return scoreService.findAll(search = search)
    }

}
