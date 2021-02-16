package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.ScoreAttack
import org.springframework.stereotype.Repository

@Repository
interface ScoreAttackRepository : BaseUniqueRepository<ScoreAttack> {

    fun findAllByUserNameAndRemovedOnIsNullOrderByTotalScore(userName: String): List<ScoreAttack>

    fun findAllByChallengeNameAndRemovedOnIsNullOrderByTotalScore(challengeName: String): List<ScoreAttack>

    fun findAllByChallengePokemonNameAndRemovedOnIsNullOrderByTotalScore(pokemonName: String): List<ScoreAttack>

    fun findAllByConsoleAndRemovedOnIsNullOrderByTotalScore(console: Console): List<ScoreAttack>

    fun findAllByRegionAndRemovedOnIsNullOrderByTotalScore(region: Region): List<ScoreAttack>

    fun findAllByRemovedOnIsNullOrderByTotalScore(): List<ScoreAttack>

}
