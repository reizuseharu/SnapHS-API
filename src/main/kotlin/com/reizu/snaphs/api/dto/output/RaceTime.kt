package com.reizu.snaphs.api.dto.output

import com.reizu.snaphs.api.entity.Outcome

data class RaceTime(

    val raceName: String,

    val runnerName: String,

    val time: Long?,

    val outcome: Outcome,

    val placement: Int?

)
