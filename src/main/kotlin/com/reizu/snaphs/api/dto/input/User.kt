package com.reizu.snaphs.api.dto.input


data class User(

    val name: String,

    val password: String,

    val salt: String,

    val country: String? = null,

    val isAdmin: Boolean = false

)
