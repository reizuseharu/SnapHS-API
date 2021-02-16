package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.Speedrun
import com.reizu.snaphs.api.repository.SpeedrunRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SpeedrunSeekService : BaseUniqueService<Speedrun>(Speedrun::class.java) {

    @Autowired
    private lateinit var speedrunRepository: SpeedrunRepository

    fun findAllByGame(gameName: String): List<Speedrun> {
        return speedrunRepository.findAllByCartGameNameAndRemovedOnIsNull(gameName)
    }

    fun findAllBySystem(systemName: String): List<Speedrun> {
        return speedrunRepository.findAllByCartSystemNameAndRemovedOnIsNull(systemName)
    }

    fun findAllByRegion(region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCartRegionAndRemovedOnIsNull(region)
    }

    fun findAllByCategoryAndGame(category: String, gameName: String): List<Speedrun> {
        return speedrunRepository.findAllByCategoryAndCartGameNameAndRemovedOnIsNull(category, gameName)
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<Speedrun> {
        return speedrunRepository.findAllByCartGameNameAndCartSystemNameAndRemovedOnIsNull(gameName, systemName)
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCartGameNameAndCartRegionAndRemovedOnIsNull(gameName, region)
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCartSystemNameAndCartRegionAndRemovedOnIsNull(systemName, region)
    }

    fun findAllByCategoryAndGameAndSystem(category: String, gameName: String, systemName: String): List<Speedrun> {
        return speedrunRepository.findAllByCategoryAndCartGameNameAndCartSystemNameAndRemovedOnIsNull(category, gameName, systemName)
    }

    fun findAllByCategoryAndGameAndRegion(category: String, gameName: String, region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCategoryAndCartGameNameAndCartRegionAndRemovedOnIsNull(category, gameName, region)
    }

    fun findAllByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCartGameNameAndCartSystemNameAndCartRegionAndRemovedOnIsNull(gameName, systemName, region)
    }

    fun find(category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Speedrun {
        return speedrunRepository
            .findFirstByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndCartVersionAndRemovedOnIsNull(category, gameName, systemName, isEmulated, region, version)
            ?: throw EntityNotFoundException()
    }

    fun findAll(category: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Speedrun> {
        return speedrunRepository.findAllByRemovedOnIsNull()
            .asSequence()
            .filter { speedrun ->  category?.let { speedrun.category == category } ?: true }
            .filter { speedrun ->  gameName?.let { speedrun.cart.game.name == gameName } ?: true }
            .filter { speedrun ->  systemName?.let { speedrun.cart.system.name == systemName } ?: true }
            .filter { speedrun ->  isEmulated?.let { speedrun.cart.system.isEmulated == isEmulated } ?: true }
            .filter { speedrun ->  region?.let { speedrun.cart.region == region } ?: true }
            .filter { speedrun ->  version?.let { speedrun.cart.version == version } ?: true }
            .toList()
    }

}
