package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.snaphs.api.dto.output.Game as GameOutput
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Game")
data class Game(

    @Column(name = "name", unique = true, nullable = false)
    val name: String,

    @Column(name = "shorthand", unique = true, nullable = false)
    val shorthand: String

) : BaseUniqueEntity() {

    val output: GameOutput
        get() {
            return GameOutput(
                name = name,
                shorthand = shorthand
            )
        }

}
