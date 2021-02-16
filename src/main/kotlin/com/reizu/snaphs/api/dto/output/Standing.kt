package com.reizu.snaphs.api.dto.output

data class Standing(

    val runnerName: String,

    val points: Int,

    val pointsPerRace: Float,

    val raceCount: Int,

    // Amount of First Places
    val wins: Int,

    // Average Time in frames
    val averageTime: Long

)
