package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.LeagueSpeedrun
import com.reizu.snaphs.api.entity.LeagueType
import com.reizu.snaphs.api.entity.Region
import org.springframework.stereotype.Repository

@Repository
interface LeagueSpeedrunRepository : BaseUniqueRepository<LeagueSpeedrun> {

    fun findAllBySpeedrunCartGameNameAndRemovedOnIsNull(gameName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartSystemNameAndRemovedOnIsNull(systemName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartRegionAndRemovedOnIsNull(region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartGameNameAndRemovedOnIsNull(leagueName: String, gameName: String): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(leagueName: String, systemName: String): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndRemovedOnIsNull(category: String, gameName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(gameName: String, systemName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(gameName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(leagueName: String, gameName: String, systemName: String): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName: String, gameName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName: String, systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(category: String, gameName: String, systemName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(category: String, gameName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(gameName: String, systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(category: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName: String, category: String, gameName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int): List<LeagueSpeedrun>

    fun findFirstByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersionAndRemovedOnIsNull(leagueName: String, category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): LeagueSpeedrun?

    fun findAllByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersionAndRemovedOnIsNull(leagueName: String, category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndLeagueTypeAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersionAndRemovedOnIsNull(leagueName: String?, leagueType: LeagueType?, category: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<LeagueSpeedrun>

    fun findAllByRemovedOnIsNull(): List<LeagueSpeedrun>

}
