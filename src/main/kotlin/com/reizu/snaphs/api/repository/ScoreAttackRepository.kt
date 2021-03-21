package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.ScoreAttack
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ScoreAttackRepository : BaseUniqueRepository<ScoreAttack> {

    fun findAllByUserNameAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(userName: String): List<ScoreAttack>

    fun findAllByChallengeNameAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(challengeName: String): List<ScoreAttack>

    fun findAllByChallengePokemonNameAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(pokemonName: String): List<ScoreAttack>

    fun findAllByConsoleAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(console: Console): List<ScoreAttack>

    fun findAllByRegionAndVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(region: Region): List<ScoreAttack>

    fun findAllByVerifiedIsTrueAndRemovedOnIsNullOrderByTotalScoreDesc(): List<ScoreAttack>

    fun findBy_idAndRemovedOnIsNull(id: UUID): ScoreAttack

    fun findAllByChallengeNameAndConsoleAndVerifiedIsTrueAndChallengeStageIsNotNullAndRemovedOnIsNullOrderByTotalScoreDesc(challengeName: String, console: Console): List<ScoreAttack>

    fun findAllByChallengeNameAndConsoleAndVerifiedIsFalseAndChallengeStageIsNotNullAndRemovedOnIsNullOrderByTotalScoreDesc(challengeName: String, console: Console): List<ScoreAttack>

    fun findAllByUserNameAndRemovedOnIsNullOrderByTotalScoreDesc(userName: String): List<ScoreAttack>

    fun findAllByChallengeNameAndRemovedOnIsNullOrderByTotalScoreDesc(challengeName: String): List<ScoreAttack>

    fun findAllByChallengePokemonNameAndRemovedOnIsNullOrderByTotalScoreDesc(pokemonName: String): List<ScoreAttack>

    fun findAllByConsoleAndRemovedOnIsNullOrderByTotalScoreDesc(console: Console): List<ScoreAttack>

    fun findAllByRegionAndRemovedOnIsNullOrderByTotalScoreDesc(region: Region): List<ScoreAttack>

    fun findAllByRemovedOnIsNullOrderByTotalScoreDesc(): List<ScoreAttack>

}
