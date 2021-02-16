package com.reizu.snaphs.api.dto.input

import com.reizu.core.util.DateTimeFormatter
import org.apache.tomcat.jni.Local
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


data class StartSeason(

    val leagueName: String,

    val season: Int,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val startedOn: LocalDateTime = LocalDateTime.now(),

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val endedOn: LocalDateTime = LocalDateTime.now(),

    val registrationEndedOn: LocalDateTime? = null,

    val qualifierRules: List<QualifierRule>? = null,

    val pointRules: List<PointRule>? = null

)
