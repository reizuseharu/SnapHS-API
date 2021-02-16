package com.reizu.snaphs.api.repository

import com.reizu.core.api.repository.BaseUniqueRepository
import com.reizu.snaphs.api.entity.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : BaseUniqueRepository<User> {

    fun findByNameAndRemovedOnIsNull(name: String): User?

    fun findAllByCountryAndRemovedOnIsNull(country: String): List<User>

}
