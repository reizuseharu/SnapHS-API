package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.Speedrun
import org.springframework.stereotype.Repository

@Repository
interface SpeedrunRepository : BaseUniqueRepository<Speedrun> {

    fun findAllByCartGameNameAndRemovedOnIsNull(gameName: String): List<Speedrun>

    fun findAllByCartSystemNameAndRemovedOnIsNull(systemName: String): List<Speedrun>

    fun findAllByCartRegionAndRemovedOnIsNull(region: Region): List<Speedrun>

    fun findAllByCategoryAndCartGameNameAndRemovedOnIsNull(category: String, gameName: String): List<Speedrun>

    fun findAllByCartGameNameAndCartSystemNameAndRemovedOnIsNull(gameName: String, systemName: String): List<Speedrun>

    fun findAllByCartGameNameAndCartRegionAndRemovedOnIsNull(gameName: String, region: Region): List<Speedrun>

    fun findAllByCartSystemNameAndCartRegionAndRemovedOnIsNull(systemName: String, region: Region): List<Speedrun>

    fun findAllByCategoryAndCartGameNameAndCartSystemNameAndRemovedOnIsNull(category: String, gameName: String, systemName: String): List<Speedrun>

    fun findAllByCategoryAndCartGameNameAndCartRegionAndRemovedOnIsNull(category: String, gameName: String, region: Region): List<Speedrun>

    fun findAllByCartGameNameAndCartSystemNameAndCartRegionAndRemovedOnIsNull(gameName: String, systemName: String, region: Region): List<Speedrun>

    fun findAllByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndRemovedOnIsNull(category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region): List<Speedrun>

    fun findFirstByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndCartVersionAndRemovedOnIsNull(category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Speedrun?

    fun findAllByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndCartVersionAndRemovedOnIsNull(category: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Speedrun>

    fun findAllByRemovedOnIsNull(): List<Speedrun>

}
