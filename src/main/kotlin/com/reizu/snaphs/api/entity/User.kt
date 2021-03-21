package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.snaphs.api.dto.output.User as UserOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "User")
data class User(

    @Column(name = "name", unique = true, nullable = false)
    val name: String,

    @Column(name = "hashedPassword", nullable = false)
    val hashedPassword: String,

    @Column(name = "salt", nullable = false)
    val salt: String,

    @Column(name = "country", nullable = true)
    val country: String? = null,

    @Column(name = "isAdmin", nullable = false)
    val admin: Boolean = false,

) : BaseUniqueEntity() {

    val output: UserOutput
        get() {
            return UserOutput(
                name = name,
                country = country,
                admin = admin
            )
        }

}
