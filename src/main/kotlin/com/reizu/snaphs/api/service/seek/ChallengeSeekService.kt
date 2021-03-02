package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Challenge
import com.reizu.snaphs.api.entity.Stage
import com.reizu.snaphs.api.repository.ChallengeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class ChallengeSeekService : BaseUniqueService<Challenge>(Challenge::class.java) {

    @Autowired
    private lateinit var challengeRepository: ChallengeRepository

    override fun findAllActive(page: Pageable?, search: String?): Iterable<Challenge> {
        return super.findAllActive(page, search)
    }

    fun findByName(name: String): Challenge {
        return challengeRepository.findByNameAndRemovedOnIsNull(name)
            ?: throw EntityNotFoundException()
    }

    fun findAllByPokemonName(pokemonName: String): List<Challenge> {
        return challengeRepository.findAllByPokemonNameAndRemovedOnIsNull(pokemonName)
    }

    fun findAllByPokemonDexNumber(pokemonDexNumber: String): List<Challenge> {
        return challengeRepository.findAllByPokemonDexNumberAndRemovedOnIsNull(pokemonDexNumber)
    }

    fun findAllByStage(stage: Stage): List<Challenge> {
        return challengeRepository.findAllByStageAndRemovedOnIsNull(stage)
    }

}
