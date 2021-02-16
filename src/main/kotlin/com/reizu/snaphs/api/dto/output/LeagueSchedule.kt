package com.reizu.snaphs.api.dto.output


data class LeagueSchedule (

    val name: String,

    val season: Int,

    val tierLevel: Int,

    val matches: List<Match>

)
