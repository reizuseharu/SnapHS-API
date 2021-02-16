package com.reizu.snaphs.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

data class Runner(

    val name: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val joinedOn: LocalDateTime

)
