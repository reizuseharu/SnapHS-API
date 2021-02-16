package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Runner
import com.reizu.snaphs.api.repository.RunnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class RunnerSeekService : BaseUniqueService<Runner>(Runner::class.java) {

    @Autowired
    private lateinit var runnerRepository: RunnerRepository

    fun findByName(name: String): Runner {
        return runnerRepository.findByNameAndRemovedOnIsNull(name) ?: throw EntityNotFoundException()
    }

}
