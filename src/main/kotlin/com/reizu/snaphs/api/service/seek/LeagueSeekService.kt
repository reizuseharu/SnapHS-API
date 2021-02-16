package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.dto.update.LeagueDivisionShift
import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.exception.LeagueHasEndedException
import com.reizu.snaphs.api.repository.LeagueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@Service
class LeagueSeekService : BaseUniqueService<League>(League::class.java) {

    @Autowired
    private lateinit var leagueRepository: LeagueRepository

    @PreAuthorize("hasRole('ADMIN')")
    @PostFilter("filterObject.relatedRunners.contains(authentication.name)")
    override fun findAllActive(page: Pageable?, search: String?): Iterable<League> {
        return super.findAllActive(page, search)
    }

    fun find(name: String, season: Int, tierLevel: Int): League {
        return leagueRepository.findByNameAndSeasonAndTierLevelAndRemovedOnIsNull(name, season, tierLevel)
            ?: throw EntityNotFoundException()
    }

    fun findAllTiers(name: String, season: Int): List<League> {
        return leagueRepository.findAllByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelAsc(name, season)
    }

    fun findBottomLeague(name: String, season: Int): League {
        return leagueRepository.findFirstByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelDesc(name, season)
            ?: throw EntityNotFoundException()
    }

}
