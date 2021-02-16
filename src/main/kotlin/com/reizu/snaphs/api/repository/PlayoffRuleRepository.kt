package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.PlayoffRule
import org.springframework.stereotype.Repository

@Repository
interface PlayoffRuleRepository : BaseUniqueRepository<PlayoffRule> {

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int): List<PlayoffRule>

    fun findByLeagueNameAndRemovedOnIsNull(leagueName: String): PlayoffRule?

}
