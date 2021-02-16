package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.Score
import com.reizu.snaphs.api.service.seek.ScoreSeekService
import com.reizu.snaphs.api.dto.input.Score as ScoreInput
import com.reizu.snaphs.api.dto.output.Score as ScoreOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScoreService {

    @Autowired
    private lateinit var scoreSeekService: ScoreSeekService

    fun create(scoreInput: ScoreInput): ScoreOutput {
        return scoreInput.run {
            val score = Score(
                special = special,
                size = size,
                pose = pose,
                isTechnique = isTechnique,
                samePokemon = samePokemon
            )
            val createdScore: Score = scoreSeekService.create(score)

            createdScore.output
        }
    }

    fun findAll(search: String?): Iterable<ScoreOutput> {
        return scoreSeekService.findAllActive(search = search).map { score -> score.output }
    }

}
