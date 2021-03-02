package com.reizu.snaphs.api.dto.input

import com.reizu.snaphs.api.entity.Role

data class Account(

    val userName: String,

    val linkedAccount: String? = null,

    val role: Role = Role.USER

)
