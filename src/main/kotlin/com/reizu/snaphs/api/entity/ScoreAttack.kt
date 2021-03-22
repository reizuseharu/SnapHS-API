package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.core.util.DateTimeFormatter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import com.reizu.snaphs.api.dto.output.ScoreAttack as ScoreAttackOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ScoreAttack")
data class ScoreAttack(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "challengeId", referencedColumnName = "id", nullable = false)
    val challenge: Challenge,

    @Column(name = "special", nullable = false)
    val special: Int = 0,

    @Column(name = "size", nullable = false)
    val size: Int = 0,

    @Column(name = "pose", nullable = false)
    val pose: Int = 0,

    @Column(name = "isTechnique", nullable = false)
    val technique: Boolean = false,

    @Column(name = "samePokemon", nullable = false)
    val samePokemon: Int = 0,

    @Column(name = "totalScore", nullable = false)
    val totalScore: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "console", nullable = false)
    val console: Console,

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = true)
    val region: Region?,

    @Column(name = "proof", nullable = true, columnDefinition = "TEXT")
    val proof: String?,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "submittedOn", nullable = false)
    val submittedOn: LocalDateTime,

    @Column(name = "isVerified", nullable = false)
    val verified: Boolean = false,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "approvedOn", nullable = true)
    val approvedOn: LocalDateTime?,

    @Column(name = "notes", nullable = true)
    val notes: String?

) : BaseUniqueEntity() {

    val output: ScoreAttackOutput
        get() {
            return ScoreAttackOutput(
                id = id,
                userName = user.name,
                challengeName = challenge.name,
                special = special,
                size = size,
                pose = pose,
                isTechnique = technique,
                samePokemon = samePokemon,
                totalScore = totalScore,
                console = console,
                region = region,
                proof = proof,
                submittedOn = submittedOn,
                isVerified = verified,
                approvedOn = approvedOn,
                notes = notes
            )
        }

    val calculatedTotalScore: Int
        get() {
            return (
                special
                + ((size + pose) * (if (technique) 2 else 1))
                + samePokemon
            )
        }

}
