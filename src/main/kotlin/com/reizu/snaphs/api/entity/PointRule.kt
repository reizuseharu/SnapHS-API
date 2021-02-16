package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.core.util.DateTimeFormatter
import jakarta.validation.constraints.Min
import com.reizu.snaphs.api.dto.output.PointRule as PointRuleOutput
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "PointRule", uniqueConstraints = [UniqueConstraint(columnNames = ["leagueId", "placement"])])
data class PointRule(

    @get:Min(1)
    @Column(name = "placement", nullable = false)
    val placement: Int,

    @get:Min(0)
    @Column(name = "amount", nullable = false)
    val amount: Int,

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueId", referencedColumnName = "id", nullable = false)
    val league: League,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "addedOn", nullable = false)
    val addedOn: LocalDateTime = LocalDateTime.now()

) : BaseUniqueEntity() {

    val output: PointRuleOutput
        get() {
            return PointRuleOutput(
                placement = placement,
                amount = amount,
                leagueName = league.name,
                season = league.season,
                tierLevel = league.tier.level,
                tierName = league.tier.name,
                addedOn = addedOn
            )
        }

}
