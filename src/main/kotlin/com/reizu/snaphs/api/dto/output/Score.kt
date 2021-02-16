package com.reizu.snaphs.api.dto.output


data class Score(

    val special: Int,

    val size: Int,

    val pose: Int,

    val isTechnique: Boolean,

    val samePokemon: Int

)
