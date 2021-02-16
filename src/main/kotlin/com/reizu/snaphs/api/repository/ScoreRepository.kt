package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Score
import org.springframework.stereotype.Repository

@Repository
interface ScoreRepository : BaseUniqueRepository<Score>
