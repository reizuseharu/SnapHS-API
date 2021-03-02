package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Score
import com.reizu.snaphs.api.repository.ScoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ScoreSeekService : BaseUniqueService<Score>(Score::class.java) {

    @Autowired
    private lateinit var scoreRepository: ScoreRepository

    override fun findAllActive(page: Pageable?, search: String?): Iterable<Score> {
        return super.findAllActive(page, search)
    }

}
