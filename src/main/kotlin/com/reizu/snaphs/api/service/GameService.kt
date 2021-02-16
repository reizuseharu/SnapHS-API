package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.input.Game as GameInput
import com.reizu.snaphs.api.dto.output.Game as GameOutput
import com.reizu.snaphs.api.entity.Game
import com.reizu.snaphs.api.service.seek.GameSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GameService {

    @Autowired
    private lateinit var gameSeekService: GameSeekService

    fun create(gameInput: GameInput): GameOutput {
        return gameInput.run {
            val game = Game(
                name = name,
                shorthand = shorthand
            )
            val createdGame: Game = gameSeekService.create(game)

            createdGame.output
        }
    }

    fun findAll(search: String?): Iterable<GameOutput> {
        return gameSeekService.findAllActive(search = search).map { game -> game.output }
    }

    fun findByShorthand(shorthand: String): GameOutput {
        return gameSeekService.findByShorthand(shorthand).output
    }

}
