package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Cart
import com.reizu.snaphs.api.entity.Region
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : BaseUniqueRepository<Cart> {

    fun findAllByGameNameAndRemovedOnIsNull(name: String): List<Cart>

    fun findAllBySystemNameAndRemovedOnIsNull(name: String): List<Cart>

    fun findAllByRegionAndRemovedOnIsNull(region: Region): List<Cart>

    fun findAllByGameNameAndSystemNameAndRemovedOnIsNull(gameName: String, systemName: String): List<Cart>

    fun findAllByGameNameAndRegionAndRemovedOnIsNull(gameName: String, region: Region): List<Cart>

    fun findAllBySystemNameAndRegionAndRemovedOnIsNull(systemName: String, region: Region): List<Cart>

    fun findFirstByGameNameAndSystemNameAndSystemEmulatedAndRegionAndVersionAndRemovedOnIsNull(gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Cart?

    fun findAllByGameNameAndSystemNameAndSystemEmulatedAndRegionAndVersionAndRemovedOnIsNull(gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Cart>

    fun findAllByRemovedOnIsNull(): List<Cart>

}
