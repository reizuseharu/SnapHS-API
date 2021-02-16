package com.reizu.snaphs.api.controller

import com.reizu.snaphs.api.dto.input.User as UserInput
import com.reizu.snaphs.api.dto.output.User as UserOutput
import com.reizu.snaphs.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody userInput: UserInput): UserOutput {
        return userService.create(userInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<UserOutput> {
        return userService.findAll(search = search)
    }

    @GetMapping(path = ["/name/{name}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByName(
        @PathVariable("name")
        name: String
    ): UserOutput {
        return userService.findByName(name)
    }

    @GetMapping(path = ["/country/{country}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllByCountry(
        @PathVariable("country")
        country: String
    ): Iterable<UserOutput> {
        return userService.findAllByCountry(country)
    }

}
