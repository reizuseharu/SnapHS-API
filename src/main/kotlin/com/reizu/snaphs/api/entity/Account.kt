package com.reizu.snaphs.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.reizu.core.entity.BaseUniqueEntity
import com.reizu.snaphs.api.dto.output.Account as AccountOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Account", uniqueConstraints = [UniqueConstraint(columnNames = ["userId"])])
data class Account(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    val user: User,

    @Column(name = "linkedAccount", nullable = true)
    val linkedAccount: String? = null

) : BaseUniqueEntity() {

    val output: AccountOutput
        get() {
            return AccountOutput(
                userName = user.name,
                linkedAccount = linkedAccount
            )
        }

}
