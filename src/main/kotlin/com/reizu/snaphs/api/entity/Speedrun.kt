package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.snaphs.api.dto.output.Speedrun as SpeedrunOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Speedrun", uniqueConstraints = [UniqueConstraint(columnNames = ["category", "cartId"])])
data class Speedrun(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "cartId", referencedColumnName = "id", nullable = false)
    val cart: Cart,

    @Column(name = "category", nullable = false)
    val category: String

) : BaseUniqueEntity() {

    val output: SpeedrunOutput
        get() {
            return SpeedrunOutput(
                category = category,
                gameName = cart.game.name,
                systemName = cart.system.name,
                isEmulated = cart.system.isEmulated,
                region = cart.region,
                version = cart.version
            )
        }

}
