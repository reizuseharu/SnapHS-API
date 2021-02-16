package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Score
import com.reizu.snaphs.api.repository.ScoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class ScoreSeekService : BaseUniqueService<Score>(Score::class.java) {

    @Autowired
    private lateinit var scoreRepository: ScoreRepository

    @PreAuthorize("hasRole('ADMIN')")
    @PostFilter("filterObject.relatedRunners.contains(authentication.name)")
    override fun findAllActive(page: Pageable?, search: String?): Iterable<Score> {
        return super.findAllActive(page, search)
    }

}
