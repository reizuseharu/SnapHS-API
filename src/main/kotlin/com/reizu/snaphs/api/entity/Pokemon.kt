package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.snaphs.api.dto.output.Pokemon as PokemonOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Pokemon")
data class Pokemon(

    @Column(name = "name", unique = true, nullable = false)
    val name: String,

    @Column(name = "dexNumber", unique = true, nullable = false)
    val dexNumber: String

) : BaseUniqueEntity() {

    val output: PokemonOutput
        get() {
            return PokemonOutput(
                name = name,
                dexNumber = dexNumber
            )
        }

}
