package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.Account
import com.reizu.snaphs.api.entity.Role
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : BaseUniqueRepository<Account> {

    fun findByUserNameAndRemovedOnIsNull(name: String): Account?

    fun findAllByUserCountryAndRemovedOnIsNull(country: String): List<Account>

    fun findAllByRoleAndRemovedOnIsNull(role: Role): List<Account>

}
