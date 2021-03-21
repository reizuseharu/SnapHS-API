package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.ScoreAttack
import com.reizu.snaphs.api.repository.ScoreAttackRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ScoreAttackSeekService : BaseUniqueService<ScoreAttack>(ScoreAttack::class.java) {

    @Autowired
    private lateinit var scoreAttackRepository: ScoreAttackRepository

    fun findAllByUser(userName: String): List<ScoreAttack> {
        return scoreAttackRepository.findAllByUserNameAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(userName)
    }

    fun findAllByChallenge(challengeName: String): List<ScoreAttack> {
        return scoreAttackRepository.findAllByChallengeNameAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(challengeName)
    }

    fun findAllByPokemon(pokemonName: String): List<ScoreAttack> {
        return scoreAttackRepository.findAllByChallengePokemonNameAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(pokemonName)
    }

    fun findAllByConsole(console: Console): List<ScoreAttack> {
        return scoreAttackRepository.findAllByConsoleAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(console)
    }

    fun findAllByRegion(region: Region): List<ScoreAttack> {
        return scoreAttackRepository.findAllByRegionAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(region)
    }

    fun findAllByChallengeAndConsoleVerified(challengeName: String, console: Console): List<ScoreAttack> {
        return scoreAttackRepository.findAllByChallengeNameAndConsoleAndVerifiedIsTrueAndChallengeStageIsNotNullAndRemovedOnIsNullOrderByTotalScoreDesc(challengeName, console)
    }

    fun findAllByChallengeAndConsoleNotVerified(challengeName: String, console: Console): List<ScoreAttack> {
        return scoreAttackRepository.findAllByChallengeNameAndConsoleAndVerifiedIsFalseAndChallengeStageIsNotNullAndRemovedOnIsNullOrderByTotalScoreDesc(challengeName, console)
    }

    fun findAllOrdered(): List<ScoreAttack> {
        return scoreAttackRepository.findAllByVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc()
    }

    fun findById(id: UUID): ScoreAttack {
        return scoreAttackRepository.findBy_idAndRemovedOnIsNull(id)
    }

}
