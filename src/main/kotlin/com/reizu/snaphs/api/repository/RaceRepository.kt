package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Race
import org.springframework.stereotype.Repository

@Repository
interface RaceRepository : BaseUniqueRepository<Race> {

    fun findByNameAndRemovedOnIsNull(name: String): Race?

}
