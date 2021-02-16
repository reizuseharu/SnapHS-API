package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Race
import com.reizu.snaphs.api.repository.RaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class RaceSeekService : BaseUniqueService<Race>(Race::class.java) {

    @Autowired
    private lateinit var raceRepository: RaceRepository

    fun findByName(name: String): Race {
        return raceRepository.findByNameAndRemovedOnIsNull(name) ?: throw EntityNotFoundException()
    }

}
