package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.PointRule
import org.springframework.stereotype.Repository

@Repository
interface PointRuleRepository : BaseUniqueRepository<PointRule> {

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int): List<PointRule>

    fun findByLeagueNameAndRemovedOnIsNull(leagueName: String): PointRule?

}
