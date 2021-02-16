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

    @Column(name = "country", nullable = false)
    val country: String,

) : BaseUniqueEntity() {

    val output: UserOutput
        get() {
            return UserOutput(
                name = name,
                country = country
            )
        }

}
