package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.DivisionShiftRule
import com.reizu.snaphs.api.entity.Shift
import org.springframework.stereotype.Repository

@Repository
interface DivisionShiftRuleRepository : BaseUniqueRepository<DivisionShiftRule> {

    fun findByLeagueNameAndRemovedOnIsNull(leagueName: String): DivisionShiftRule?

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule>

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int, shift: Shift): List<DivisionShiftRule>

}
