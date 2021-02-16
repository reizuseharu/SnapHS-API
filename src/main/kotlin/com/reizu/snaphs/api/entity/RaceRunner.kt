package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.core.util.DateTimeFormatter
import com.reizu.snaphs.api.dto.output.RaceTime as RaceTimeOutput
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "RaceRunner", uniqueConstraints = [UniqueConstraint(columnNames = ["raceId", "runnerId"])])
data class RaceRunner(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "raceId", referencedColumnName = "id", nullable = false)
    val race: Race,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "runnerId", referencedColumnName = "id", nullable = false)
    val runner: Runner,

    // Time in frames - Convert to human readable on frontend
    @Column(name = "time")
    var time: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "outcome", nullable = false)
    var outcome: Outcome = Outcome.PENDING_VERIFICATION,

    @Column(name = "placement")
    var placement: Int? = null,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @CreatedDate
    @CreationTimestamp
    @Column(name = "joinedOn", updatable = false, nullable = false)
    val joinedOn: LocalDateTime = LocalDateTime.now()

) : BaseUniqueEntity() {

    val output: RaceTimeOutput
        get() {
            return RaceTimeOutput(
                raceName = race.name,
                runnerName = runner.name,
                time = time,
                outcome = outcome,
                placement = placement
            )
        }

}
