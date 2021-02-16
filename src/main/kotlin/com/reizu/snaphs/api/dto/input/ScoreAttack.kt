package com.reizu.snaphs.api.dto.input

import com.reizu.core.util.DateTimeFormatter
import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class ScoreAttack(

    val userName: String,

    val challengeName: String,

    val score: Score,

    val console: Console,

    val region: Region,

    val picture: String?,

    val video: String?,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val submittedOn: LocalDateTime = LocalDateTime.now(),

    val isVerified: Boolean = false,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val approvedOn: LocalDateTime?

)
