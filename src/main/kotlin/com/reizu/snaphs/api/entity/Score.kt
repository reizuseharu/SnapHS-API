package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.snaphs.api.dto.output.Score as ScoreOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Score")
data class Score(

    @Column(name = "special", nullable = false)
    val special: Int = 0,

    @Column(name = "size", nullable = false)
    val size: Int = 0,

    @Column(name = "pose", nullable = false)
    val pose: Int = 0,

    @Column(name = "isTechnique", nullable = false)
    val technique: Boolean = false,

    @Column(name = "samePokemon", nullable = false)
    val samePokemon: Int = 0

) : BaseUniqueEntity() {

    val output: ScoreOutput
        get() {
            return ScoreOutput(
                special = special,
                size = size,
                pose = pose,
                isTechnique = technique,
                samePokemon = samePokemon
            )
        }

    val totalScore: Int
        get() {
            return (
                special
                + ((size + pose) * (if (technique) 2 else 1))
                + samePokemon
            )
        }

}
