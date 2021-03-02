package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Pokemon
import com.reizu.snaphs.api.repository.PokemonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class PokemonSeekService : BaseUniqueService<Pokemon>(Pokemon::class.java) {

    @Autowired
    private lateinit var pokemonRepository: PokemonRepository

    override fun findAllActive(page: Pageable?, search: String?): Iterable<Pokemon> {
        return super.findAllActive(page, search)
    }

    fun findByName(name: String): Pokemon {
        return pokemonRepository.findByNameAndRemovedOnIsNull(name)
            ?: throw EntityNotFoundException()
    }

    fun findByDexNumber(dexNumber: String): Pokemon {
        return pokemonRepository.findByDexNumberAndRemovedOnIsNull(dexNumber)
            ?: throw EntityNotFoundException()
    }

}
