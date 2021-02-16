package com.reizu.snaphs.api.dto.input

import com.reizu.core.util.DateTimeFormatter
import org.apache.tomcat.jni.Local
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


data class LowerTier(

    val leagueName: String,

    val season: Int,

    val parentTierLevel: Int,

    val tierName: String,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val startedOn: LocalDateTime = LocalDateTime.now(),

    val defaultTime: Long? = null,

    val defaultPoints: Int? = null,

    val runnerLimit: Int? = null,

    val registrationEndedOn: LocalDateTime? = null,

    val qualifierRules: List<QualifierRule>? = null,

    val pointRules: List<PointRule>? = null,

    val divisionShifts: Int = 0,

    val promotionRules: List<QualifierRule>? = null,

    val relegationRules: List<QualifierRule>? = null

)
