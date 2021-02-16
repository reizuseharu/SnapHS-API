package com.reizu.snaphs.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.snaphs.api.entity.Qualifier
import java.time.LocalDateTime

data class PlayoffRule(

    val qualifier: Qualifier,

    val count: Int,

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val tierName: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val addedOn: LocalDateTime,

    val order: Int

)
