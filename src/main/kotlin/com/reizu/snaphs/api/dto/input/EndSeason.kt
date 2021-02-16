package com.reizu.snaphs.api.dto.input

import com.reizu.core.util.DateTimeFormatter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


data class EndSeason(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val endedOn: LocalDateTime = LocalDateTime.now()

)
