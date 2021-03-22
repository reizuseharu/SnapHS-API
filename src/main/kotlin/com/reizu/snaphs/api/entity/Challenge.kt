package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.snaphs.api.dto.output.Challenge as ChallengeOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Challenge", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "pokemonId", "stage"])])
data class Challenge(

    @Column(name = "name", unique = true, nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "pokemonId", referencedColumnName = "id", nullable = true)
    val pokemon: Pokemon?,

    @Enumerated(EnumType.STRING)
    @Column(name = "stage", nullable = true)
    val stage: Stage?

) : BaseUniqueEntity() {

    val output: ChallengeOutput
        get() {
            return ChallengeOutput(
                name = name,
                pokemonName = pokemon?.name,
                stage = stage
            )
        }

}
