package com.reizu.snaphs.api.entity

import javax.persistence.Embeddable
import jakarta.validation.constraints.Min

@Embeddable
data class Tier(

    val name: String,

    @get:Min(0)
    val level: Int

)
