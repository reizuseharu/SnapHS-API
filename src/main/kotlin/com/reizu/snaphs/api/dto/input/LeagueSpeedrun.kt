package com.reizu.snaphs.api.dto.input

import com.reizu.snaphs.api.entity.Region


data class LeagueSpeedrun(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val category: String,

    val gameName: String,

    val systemName: String,

    val isEmulated: Boolean = false,

    val region: Region = Region.ANY,

    val version: String = "ANY"

)
