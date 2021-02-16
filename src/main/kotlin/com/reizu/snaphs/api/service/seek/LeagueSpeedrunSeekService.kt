package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.LeagueSpeedrun
import com.reizu.snaphs.api.entity.LeagueType
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.repository.LeagueSpeedrunRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class LeagueSpeedrunSeekService : BaseUniqueService<LeagueSpeedrun>(LeagueSpeedrun::class.java) {

    @Autowired
    private lateinit var leagueSpeedrunRepository: LeagueSpeedrunRepository

    // ! Need to add findByVersion -- possibly at Controller level for convenience

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName, season, tierLevel)
    }

    fun findAllByGame(gameName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllBySpeedrunCartGameNameAndRemovedOnIsNull(gameName)
    }

    fun findAllBySystem(systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllBySpeedrunCartSystemNameAndRemovedOnIsNull(systemName)
    }

    fun findAllByRegion(region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllBySpeedrunCartRegionAndRemovedOnIsNull(region)
    }

    fun findAllByLeagueAndGame(leagueName: String, gameName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartGameNameAndRemovedOnIsNull(leagueName, gameName)
    }

    fun findAllByLeagueAndSystem(leagueName: String, systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(leagueName, systemName)
    }

    fun findAllByLeagueAndRegion(leagueName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName, region)
    }

    fun findAllByCategoryAndGame(category: String, gameName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndRemovedOnIsNull(category, gameName)
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(gameName, systemName)
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(gameName, region)
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(systemName, region)
    }

    fun findAllByLeagueAndGameAndSystem(leagueName: String, gameName: String, systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(leagueName, gameName, systemName)
    }

    fun findAllByLeagueAndGameAndRegion(leagueName: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName, gameName, region)
    }

    fun findAllByLeagueAndSystemAndRegion(leagueName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName, systemName, region)
    }

    fun findAllByCategoryAndGameAndSystem(category: String, gameName: String, systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndRemovedOnIsNull(category, gameName, systemName)
    }

    fun findAllByCategoryAndGameAndRegion(category: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(category, gameName, region)
    }

    fun findAllByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(gameName, systemName, region)
    }

    fun findAllByCategoryAndGameAndSystemAndRegion(category: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(category, gameName, systemName, region)
    }

    fun findAllByLeagueAndGameAndSystemAndRegion(leagueName: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName, gameName, systemName, region)
    }

    fun findAllByLeagueAndCategoryAndGameAndRegion(leagueName: String, category: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegionAndRemovedOnIsNull(leagueName, category, gameName, region)
    }

    fun find(leagueName: String, category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): LeagueSpeedrun {
        return leagueSpeedrunRepository
            .findFirstByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersionAndRemovedOnIsNull(leagueName, category, gameName, systemName, isEmulated, region, version)
            ?: throw EntityNotFoundException()
    }

    fun findAll(leagueName: String?, leagueType: LeagueType?, category: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllByRemovedOnIsNull()
            .asSequence()
            .filter { leagueSpeedrun ->  leagueName?.let { leagueSpeedrun.league.name == leagueName } ?: true }
            .filter { leagueSpeedrun ->  leagueType?.let { leagueSpeedrun.league.type == leagueType } ?: true }
            .filter { leagueSpeedrun ->  category?.let { leagueSpeedrun.speedrun.category == category } ?: true }
            .filter { leagueSpeedrun ->  gameName?.let { leagueSpeedrun.speedrun.cart.game.name == gameName } ?: true }
            .filter { leagueSpeedrun ->  systemName?.let { leagueSpeedrun.speedrun.cart.system.name == systemName } ?: true }
            .filter { leagueSpeedrun ->  isEmulated?.let { leagueSpeedrun.speedrun.cart.system.isEmulated == isEmulated } ?: true }
            .filter { leagueSpeedrun ->  region?.let { leagueSpeedrun.speedrun.cart.region == region } ?: true }
            .filter { leagueSpeedrun ->  version?.let { leagueSpeedrun.speedrun.cart.version == version } ?: true }
            .toList()
    }

}
