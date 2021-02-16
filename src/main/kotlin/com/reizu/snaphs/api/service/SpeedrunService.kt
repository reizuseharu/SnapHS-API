package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.dto.input.Speedrun as SpeedrunInput
import com.reizu.snaphs.api.dto.output.Speedrun as SpeedrunOutput
import com.reizu.snaphs.api.service.seek.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpeedrunService {

    @Autowired
    private lateinit var speedrunSeekService: SpeedrunSeekService

    @Autowired
    private lateinit var cartSeekService: CartSeekService

    fun create(speedrunInput: SpeedrunInput): SpeedrunOutput {
        return speedrunInput.run {
            val cart: Cart = cartSeekService.find(gameName, systemName, isEmulated, region, version)

            val speedrun = Speedrun(
                cart = cart,
                category = category
            )
            val createdSpeedrun: Speedrun = speedrunSeekService.create(speedrun)

            createdSpeedrun.output
        }
    }

    fun findAll(search: String?): Iterable<SpeedrunOutput> {
        return speedrunSeekService.findAllActive(search = search).map { speedrun -> speedrun.output }
    }

    fun findAll(
        category: String?,
        gameName: String?,
        systemName: String?,
        isEmulated: Boolean?,
        region: Region?,
        version: String?
    ): Iterable<SpeedrunOutput> {
        return speedrunSeekService
            .findAll(category, gameName, systemName, isEmulated, region, version)
            .map { speedrun -> speedrun.output }
    }

}
