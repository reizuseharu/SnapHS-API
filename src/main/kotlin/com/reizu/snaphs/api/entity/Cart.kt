package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.snaphs.api.dto.output.Cart as CartOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Cart", uniqueConstraints = [UniqueConstraint(columnNames = ["gameId", "systemId", "region", "version"])])
data class Cart(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "gameId", referencedColumnName = "id", nullable = false)
    val game: Game,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "systemId", referencedColumnName = "id", nullable = false)
    val system: System,

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    val region: Region = Region.ANY,

    @Column(name = "version", nullable = false)
    val version: String = "ANY"

) : BaseUniqueEntity() {

    val output: CartOutput
        get() {
            return CartOutput(
                gameName = game.name,
                systemName = system.name,
                isEmulated = system.isEmulated,
                region = region,
                version = version
            )
        }

}
