package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Game
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : BaseUniqueRepository<Game> {

    fun findByNameAndRemovedOnIsNull(name: String): Game?

    fun findByShorthandAndRemovedOnIsNull(shorthand: String): Game?

}
