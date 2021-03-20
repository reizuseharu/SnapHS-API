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
    @Column(name = "region", nullable = false)
    val region: Region,

    @Column(name = "picture", nullable = true)
    val picture: String?,

    // - Add pictureUrl
    // - Change to videoUrl
    @Column(name = "video", nullable = true)
    val video: String?,

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
    val approvedOn: LocalDateTime?

) : BaseUniqueEntity() {

    val output: ScoreAttackOutput
        get() {
            return ScoreAttackOutput(
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
                picture = picture,
                video = video,
                submittedOn = submittedOn,
                isVerified = verified,
                approvedOn = approvedOn
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
