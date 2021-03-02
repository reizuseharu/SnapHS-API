package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.User
import com.reizu.snaphs.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserSeekService : BaseUniqueService<User>(User::class.java) {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun findAllActive(page: Pageable?, search: String?): Iterable<User> {
        return super.findAllActive(page, search)
    }

    fun findByName(name: String): User {
        return userRepository.findByNameAndRemovedOnIsNull(name)
            ?: throw EntityNotFoundException()
    }

    fun findAllByCountry(country: String): List<User> {
        return userRepository.findAllByCountryAndRemovedOnIsNull(country)
    }

}
