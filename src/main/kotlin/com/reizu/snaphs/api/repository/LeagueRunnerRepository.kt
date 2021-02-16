package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.LeagueRunner
import org.springframework.stereotype.Repository

@Repository
interface LeagueRunnerRepository : BaseUniqueRepository<LeagueRunner> {

    fun findAllByRunnerNameAndRemovedOnIsNull(runnerName: String): List<LeagueRunner>

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int): List<LeagueRunner>

    fun findByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRunnerNameAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int, runnerName: String): LeagueRunner?

}
