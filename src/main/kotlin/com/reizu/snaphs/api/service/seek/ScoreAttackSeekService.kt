package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.ScoreAttack
import com.reizu.snaphs.api.repository.ScoreAttackRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class ScoreAttackSeekService : BaseUniqueService<ScoreAttack>(ScoreAttack::class.java) {

    @Autowired
    private lateinit var scoreAttackRepository: ScoreAttackRepository

    fun findAllByUser(userName: String): List<ScoreAttack> {
        return scoreAttackRepository.findAllByUserNameAndRemovedOnIsNullOrderByTotalScore(userName)
    }

    fun findAllByChallenge(challengeName: String): List<ScoreAttack> {
        return scoreAttackRepository.findAllByChallengeNameAndRemovedOnIsNullOrderByTotalScore(challengeName)
    }

    fun findAllByPokemon(pokemonName: String): List<ScoreAttack> {
        return scoreAttackRepository.findAllByChallengePokemonNameAndRemovedOnIsNullOrderByTotalScore(pokemonName)
    }

    fun findAllByConsole(console: Console): List<ScoreAttack> {
        return scoreAttackRepository.findAllByConsoleAndRemovedOnIsNullOrderByTotalScore(console)
    }

    fun findAllByRegion(region: Region): List<ScoreAttack> {
        return scoreAttackRepository.findAllByRegionAndRemovedOnIsNullOrderByTotalScore(region)
    }

    fun findAllOrdered(): List<ScoreAttack> {
        return scoreAttackRepository.findAllByRemovedOnIsNullOrderByTotalScore()
    }

}
