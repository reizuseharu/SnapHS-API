package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Pokemon
import org.springframework.stereotype.Repository

@Repository
interface PokemonRepository : BaseUniqueRepository<Pokemon> {

    fun findByNameAndRemovedOnIsNull(name: String): Pokemon?

    fun findByDexNumberAndRemovedOnIsNull(dexNumber: String): Pokemon?

}
