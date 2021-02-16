package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.League
import org.springframework.stereotype.Repository

@Repository
interface LeagueRepository : BaseUniqueRepository<League> {

    fun findByNameAndSeasonAndTierLevelAndRemovedOnIsNull(name: String, season: Int, tierLevel: Int): League?

    fun findAllByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelAsc(name: String, season: Int): List<League>

    fun findFirstByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelDesc(name: String, season: Int): League?

}
