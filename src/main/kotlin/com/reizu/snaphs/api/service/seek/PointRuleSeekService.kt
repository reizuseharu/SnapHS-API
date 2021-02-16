package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.PointRule
import com.reizu.snaphs.api.repository.PointRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class PointRuleSeekService : BaseUniqueService<PointRule>(PointRule::class.java) {

    @Autowired
    private lateinit var pointRuleRepository: PointRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<PointRule> {
        return pointRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName, season, tierLevel)
    }

    fun findByLeague(leagueName: String): PointRule {
        return pointRuleRepository.findByLeagueNameAndRemovedOnIsNull(leagueName)
            ?: throw EntityNotFoundException()
    }

    fun delete(pointRule: PointRule) {
        pointRuleRepository.delete(pointRule)
    }

    fun deleteAll(pointRules: Iterable<PointRule>) {
        pointRuleRepository.deleteAll(pointRules)
    }

}
