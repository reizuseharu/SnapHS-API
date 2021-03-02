package com.reizu.snaphs.api.dto.output

import com.reizu.snaphs.api.entity.Role

data class Account(

    val userName: String,

    val linkedAccount: String?,

    val role: Role

)
