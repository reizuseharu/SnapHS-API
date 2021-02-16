package com.reizu.snaphs.api.dto.output

import com.reizu.snaphs.api.entity.Region


data class LeagueSpeedrun(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val category: String,

    val gameName: String,

    val systemName: String,

    val isEmulated: Boolean,

    val region: Region,

    val version: String

)
