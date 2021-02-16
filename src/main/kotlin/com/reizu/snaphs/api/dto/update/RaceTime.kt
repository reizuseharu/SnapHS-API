package com.reizu.snaphs.api.dto.update

import com.reizu.snaphs.api.entity.Outcome

data class RaceTime(

    val runnerName: String,

    val raceName: String,

    val time: Long, // - Convert in frontend

    val outcome: Outcome

)
