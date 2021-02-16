package com.reizu.snaphs.api.dto.input

import com.reizu.core.util.DateTimeFormatter
import com.reizu.snaphs.api.entity.LeagueType
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class League(

    val name: String,

    val type: LeagueType = LeagueType.POOL,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val startedOn: LocalDateTime = LocalDateTime.now(),

    val defaultTime: Long,

    val defaultPoints: Int,

    val tierName: String,

    val runnerLimit: Int,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val registrationEndedOn: LocalDateTime? = null,

    val promotions: Int = 0,

    val relegations: Int = 0,

    val qualifierRules: List<QualifierRule>,

    val pointRules: List<PointRule>,

    val promotionRules: List<QualifierRule>,

    val relegationRules: List<QualifierRule>

)
