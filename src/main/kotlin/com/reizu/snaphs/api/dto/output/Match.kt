package com.reizu.snaphs.api.dto.output

import java.time.LocalDateTime

data class Match (

    val race: String,

    val homeRunner: String,

    val awayRunner: String,

    val startedOn: LocalDateTime

)
