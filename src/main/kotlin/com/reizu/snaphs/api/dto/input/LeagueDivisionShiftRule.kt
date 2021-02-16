package com.reizu.snaphs.api.dto.input


data class LeagueDivisionShiftRule(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val tierName: String,

    val promotionRules: List<QualifierRule> = listOf(),

    val relegationRules: List<QualifierRule> = listOf()

)
