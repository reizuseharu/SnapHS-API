package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.Account as AccountInput
import com.reizu.snaphs.api.dto.output.Account as AccountOutput
import com.reizu.snaphs.api.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
class AccountController {

    @Autowired
    private lateinit var accountService: AccountService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody accountInput: AccountInput): AccountOutput {
        return accountService.create(accountInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<AccountOutput> {
        return accountService.findAll(search = search)
    }

    @GetMapping(path = ["/name/{userName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByUserName(
        @PathVariable("userName")
        userName: String
    ): AccountOutput {
        return accountService.findByUserName(userName)
    }

    @GetMapping(path = ["/country/{country}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByUserCountry(
        @PathVariable("country")
        country: String
    ): Iterable<AccountOutput> {
        return accountService.findAllByUserCountry(country)
    }

}
