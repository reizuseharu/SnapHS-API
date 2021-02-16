package com.reizu.snaphs.api.dto.input

import com.reizu.snaphs.api.entity.Stage

data class Challenge(

    val name: String,

    val pokemonName: String,

    val stage: Stage?

)
