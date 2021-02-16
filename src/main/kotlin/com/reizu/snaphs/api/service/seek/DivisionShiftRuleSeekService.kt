package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.DivisionShiftRule
import com.reizu.snaphs.api.entity.Shift
import com.reizu.snaphs.api.repository.DivisionShiftRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class DivisionShiftRuleSeekService : BaseUniqueService<DivisionShiftRule>(DivisionShiftRule::class.java) {

    @Autowired
    private lateinit var divisionShiftRuleRepository: DivisionShiftRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return divisionShiftRuleRepository.findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName, season, tierLevel)
    }

    fun findAllPromotionByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(leagueName, season, tierLevel, Shift.PROMOTION)
    }

    fun findAllRelegationByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(leagueName, season, tierLevel, Shift.RELEGATION)
    }

    fun findByLeague(leagueName: String): DivisionShiftRule {
        return divisionShiftRuleRepository.findByLeagueNameAndRemovedOnIsNull(leagueName)
            ?: throw EntityNotFoundException()
    }

    fun delete(divisionShiftRule: DivisionShiftRule) {
        divisionShiftRuleRepository.delete(divisionShiftRule)
    }

    fun deleteAll(divisionShiftRules: Iterable<DivisionShiftRule>) {
        divisionShiftRuleRepository.deleteAll(divisionShiftRules)
    }

}
