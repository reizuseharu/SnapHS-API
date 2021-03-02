package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.Account
import com.reizu.snaphs.api.entity.Role
import com.reizu.snaphs.api.entity.User
import com.reizu.snaphs.api.service.seek.AccountSeekService
import com.reizu.snaphs.api.service.seek.UserSeekService
import com.reizu.snaphs.api.dto.input.Account as AccountInput
import com.reizu.snaphs.api.dto.output.Account as AccountOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService {

    @Autowired
    private lateinit var accountSeekService: AccountSeekService

    @Autowired
    private lateinit var userSeekService: UserSeekService

    fun create(accountInput: AccountInput): AccountOutput {
        return accountInput.run {
            val user: User = userSeekService.findByName(userName)

            val account = Account(
                user = user,
                linkedAccount = linkedAccount,
                role = role
            )
            val createdAccount: Account = accountSeekService.create(account)

            createdAccount.output
        }
    }

    fun findAll(search: String?): Iterable<AccountOutput> {
        return accountSeekService.findAllActive(search = search).map { account -> account.output }
    }

    fun findByUserName(userName: String): AccountOutput {
        return accountSeekService.findByName(userName).output
    }

    fun findAllByUserCountry(country: String): Iterable<AccountOutput> {
        return accountSeekService.findAllByCountry(country)
            .map { account -> account.output}
    }

    fun findAllByRole(role: Role): Iterable<AccountOutput> {
        return accountSeekService.findAllByRole(role)
            .map { account -> account.output}
    }

}
