package com.reizu.snaphs.api.service.seek

import com.reizu.core.api.service.BaseUniqueService
import com.reizu.snaphs.api.entity.Account
import com.reizu.snaphs.api.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class AccountSeekService : BaseUniqueService<Account>(Account::class.java) {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @PreAuthorize("hasRole('ADMIN')")
    @PostFilter("filterObject.relatedRunners.contains(authentication.name)")
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

}
