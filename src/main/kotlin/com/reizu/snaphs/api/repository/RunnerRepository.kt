package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Runner
import org.springframework.stereotype.Repository

@Repository
interface RunnerRepository : BaseUniqueRepository<Runner> {

    fun findByNameAndRemovedOnIsNull(name: String): Runner?

}
