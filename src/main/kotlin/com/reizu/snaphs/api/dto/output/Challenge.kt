package com.reizu.snaphs.api.dto.output

import com.reizu.snaphs.api.entity.Stage

data class Challenge(

    val name: String,

    val pokemonName: String?,

    val stage: Stage?

)
