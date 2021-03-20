package com.reizu.snaphs.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import java.time.LocalDateTime

data class ScoreAttack(

    val userName: String,

    val challengeName: String,

    val special: Int,

    val size: Int,

    val pose: Int,

    val isTechnique: Boolean,

    val samePokemon: Int,

    val totalScore: Int,

    val console: Console,

    val region: Region,

    val picture: String?,

    val video: String?,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val submittedOn: LocalDateTime,

    val isVerified: Boolean = false,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val approvedOn: LocalDateTime?

)
