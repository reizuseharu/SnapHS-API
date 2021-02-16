package com.reizu.snaphs.api.dto.update


data class LeagueDivisionShift (

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val promotions: Int? = null,

    val relegations: Int? = null

)
