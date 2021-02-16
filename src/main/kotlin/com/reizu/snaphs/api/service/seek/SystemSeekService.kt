package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.System
import com.reizu.snaphs.api.repository.SystemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SystemSeekService : BaseUniqueService<System>(System::class.java) {

    @Autowired
    private lateinit var systemRepository: SystemRepository

    fun findByName(name: String): System {
        return systemRepository.findByNameAndRemovedOnIsNull(name) ?: throw EntityNotFoundException()
    }

}
