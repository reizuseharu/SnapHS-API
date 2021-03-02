package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Account
import com.reizu.snaphs.api.entity.Role
import com.reizu.snaphs.api.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class AccountSeekService : BaseUniqueService<Account>(Account::class.java) {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    override fun findAllActive(page: Pageable?, search: String?): Iterable<Account> {
        return super.findAllActive(page, search)
    }

    fun findByName(userName: String): Account {
        return accountRepository.findByUserNameAndRemovedOnIsNull(userName)
            ?: throw EntityNotFoundException()
    }

    fun findAllByCountry(userCountry: String): List<Account> {
        return accountRepository.findAllByUserCountryAndRemovedOnIsNull(userCountry)
    }

    fun findAllByRole(role: Role): List<Account> {
        return accountRepository.findAllByRoleAndRemovedOnIsNull(role)
    }

}
