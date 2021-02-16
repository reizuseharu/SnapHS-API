package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.System
import org.springframework.stereotype.Repository

@Repository
interface SystemRepository : BaseUniqueRepository<System> {

    fun findByNameAndRemovedOnIsNull(name: String): System?

}
