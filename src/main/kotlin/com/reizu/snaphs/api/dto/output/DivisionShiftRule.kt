package com.reizu.snaphs.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.snaphs.api.entity.Qualifier
import com.reizu.snaphs.api.entity.Shift
import java.time.LocalDateTime

data class DivisionShiftRule(

    val qualifier: Qualifier,

    val count: Int,

    val shift: Shift,

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val tierName: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val addedOn: LocalDateTime,

    val order: Int

)
