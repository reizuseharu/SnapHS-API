package com.reizu.snaphs.api.dto.input

import com.reizu.snaphs.api.entity.Outcome
import java.time.LocalDateTime

data class RaceTime(

    val runnerName: String,

    val raceName: String,

    val time: Long? = null, // - Convert in frontend

    val outcome: Outcome = Outcome.PENDING_VERIFICATION,

    val joinedOn: LocalDateTime = LocalDateTime.now()

)
