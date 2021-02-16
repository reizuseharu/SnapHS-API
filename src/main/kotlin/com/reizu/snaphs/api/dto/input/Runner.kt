package com.reizu.snaphs.api.dto.input

import com.reizu.core.util.DateTimeFormatter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


data class Runner(

    val name: String,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val joinedOn: LocalDateTime = LocalDateTime.now()

)
