package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.core.util.DateTimeFormatter
import com.reizu.snaphs.api.dto.output.League as LeagueOutput
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*
import jakarta.validation.constraints.Min

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "League", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "season", "tierLevel"])])
data class League(

    @Column(name = "name", nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: LeagueType = LeagueType.POOL,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "startedOn", nullable = false)
    val startedOn: LocalDateTime,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "endedOn")
    var endedOn: LocalDateTime? = null,

    @get:Min(0L)
    @Column(name = "defaultTime", nullable = false)
    val defaultTime: Long,

    @get:Min(0)
    @Column(name = "defaultPoints", nullable = false)
    val defaultPoints: Int,

    @get:Min(0)
    @Column(name = "season", nullable = false)
    val season: Int,

    @Embedded
    @AttributeOverrides(value = [
        AttributeOverride(name = "name", column = Column(name = "tierName", nullable = false)),
        AttributeOverride(name = "level", column = Column(name = "tierLevel", nullable = false))
    ])
    val tier: Tier,

    @get:Min(0)
    @Column(name = "runnerLimit", nullable = false)
    val runnerLimit: Int,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "registrationEndedOn")
    var registrationEndedOn: LocalDateTime? = null,

    @get:Min(0)
    @Column(name = "promotions", nullable = false)
    var promotions: Int = 0,

    @get:Min(0)
    @Column(name = "relegations", nullable = false)
    var relegations: Int = 0

) : BaseUniqueEntity() {

    @JsonBackReference
    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var races: Set<Race> = emptySet()

    @JsonBackReference
    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var playoffRules: List<PlayoffRule> = emptyList()

    @JsonBackReference
    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var pointRules: Set<PointRule> = emptySet()

    @JsonBackReference
    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var divisionShiftRules: List<DivisionShiftRule> = emptyList()

    val promotionRules: List<DivisionShiftRule>
        get() {
            return divisionShiftRules.filter { divisionShiftRule -> divisionShiftRule.shift == Shift.PROMOTION }
        }

    val relegationRules: List<DivisionShiftRule>
        get() {
            return divisionShiftRules.filter { divisionShiftRule -> divisionShiftRule.shift == Shift.RELEGATION }
        }

    val output: LeagueOutput
        get() {
            return LeagueOutput(
                name = name,
                type = type,
                startedOn = startedOn,
                endedOn = endedOn,
                defaultTime = defaultTime,
                defaultPoints = defaultPoints,
                season = season,
                tierLevel = tier.level,
                tierName = tier.name,
                runnerLimit = runnerLimit,
                registrationEndedOn = registrationEndedOn,
                promotions = promotions,
                relegations = relegations
            )
        }

    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var leagueRunners: Set<LeagueRunner> = emptySet()

    val relatedRunners: Set<String>
        get() {
            return leagueRunners.map { leagueRunner -> leagueRunner.runner.name }.toSet()
        }
}
