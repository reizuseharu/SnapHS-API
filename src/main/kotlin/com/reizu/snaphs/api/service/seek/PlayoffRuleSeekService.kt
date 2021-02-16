package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.PlayoffRule
import com.reizu.snaphs.api.repository.PlayoffRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class PlayoffRuleSeekService : BaseUniqueService<PlayoffRule>(PlayoffRule::class.java) {

    @Autowired
    private lateinit var playoffRuleRepository: PlayoffRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<PlayoffRule> {
        return playoffRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName, season, tierLevel)
    }

    fun findByLeague(leagueName: String): PlayoffRule {
        return playoffRuleRepository.findByLeagueNameAndRemovedOnIsNull(leagueName)
            ?: throw EntityNotFoundException()
    }

    fun delete(playoffRule: PlayoffRule) {
        playoffRuleRepository.delete(playoffRule)
    }

    fun deleteAll(playoffRules: Iterable<PlayoffRule>) {
        playoffRuleRepository.deleteAll(playoffRules)
    }

}
