package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Game
import com.reizu.snaphs.api.repository.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class GameSeekService : BaseUniqueService<Game>(Game::class.java) {

    @Autowired
    private lateinit var gameRepository: GameRepository

    fun findByName(name: String): Game {
        return gameRepository.findByNameAndRemovedOnIsNull(name) ?: throw EntityNotFoundException()
    }

    fun findByShorthand(shorthand: String): Game {
        return gameRepository.findByShorthandAndRemovedOnIsNull(shorthand) ?: throw EntityNotFoundException()
    }

}
