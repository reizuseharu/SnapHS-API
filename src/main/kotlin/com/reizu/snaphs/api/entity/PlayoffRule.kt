package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.core.util.DateTimeFormatter
import com.reizu.snaphs.api.dto.output.PlayoffRule as PlayoffRuleOutput
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "PlayoffRule", uniqueConstraints = [UniqueConstraint(columnNames = ["leagueId", "order"])])
data class PlayoffRule(

    @Enumerated(EnumType.STRING)
    @Column(name = "qualifier", nullable = false)
    val qualifier: Qualifier,

    @Column(name = "count", nullable = false)
    val count: Int,

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueId", referencedColumnName = "id", nullable = false)
    val league: League,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "addedOn", nullable = false)
    val addedOn: LocalDateTime = LocalDateTime.now(),

    @Column(name = "order", nullable = false)
    val order: Int

) : BaseUniqueEntity() {

    val output: PlayoffRuleOutput
        get() {
            return PlayoffRuleOutput(
                qualifier = qualifier,
                count = count,
                leagueName = league.name,
                season = league.season,
                tierLevel = league.tier.level,
                tierName = league.tier.name,
                addedOn = addedOn,
                order = order
            )
        }

}
