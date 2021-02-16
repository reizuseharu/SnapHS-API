package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Challenge
import com.reizu.snaphs.api.entity.Stage
import org.springframework.stereotype.Repository

@Repository
interface ChallengeRepository : BaseUniqueRepository<Challenge> {

    fun findByNameAndRemovedOnIsNull(name: String): Challenge?

    fun findAllByPokemonNameAndRemovedOnIsNull(pokemonName: String): List<Challenge>

    fun findAllByPokemonDexNumberAndRemovedOnIsNull(pokemonDexNumber: String): List<Challenge>

    fun findAllByStageAndRemovedOnIsNull(stage: Stage): List<Challenge>

}
