package com.reizu.snaphs.api.dto.input


data class Score(

    val special: Int = 0,

    val size: Int,

    val pose: Int,

    val isTechnique: Boolean,

    val samePokemon: Int = 0

)
