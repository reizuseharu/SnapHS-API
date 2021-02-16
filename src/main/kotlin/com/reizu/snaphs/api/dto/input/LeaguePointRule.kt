package com.reizu.snaphs.api.dto.input


data class LeaguePointRule(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val tierName: String,

    val pointRules: List<PointRule>

)
