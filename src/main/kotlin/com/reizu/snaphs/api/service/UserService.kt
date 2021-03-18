package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.User
import com.reizu.snaphs.api.service.seek.UserSeekService
import com.reizu.snaphs.api.dto.input.User as UserInput
import com.reizu.snaphs.api.dto.output.User as UserOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom

@Service
class UserService {

    @Autowired
    private lateinit var userSeekService: UserSeekService

    fun create(userInput: UserInput): UserOutput {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)

        val messageDigest = MessageDigest.getInstance("SHA-512")
        messageDigest.update(salt)

        return userInput.run {
            val hashedPassword = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
            val user = User(
                name = name,
                hashedPassword = hashedPassword.toString(),
                salt = salt.toString(),
                country = country,
                admin = isAdmin
            )
            val createdUser: User = userSeekService.create(user)

            createdUser.output
        }
    }

    fun findAll(search: String?): Iterable<UserOutput> {
        return userSeekService.findAllActive(search = search).map { user -> user.output }
    }

    fun findByName(name: String): UserOutput {
        return userSeekService.findByName(name).output
    }

    fun findAllByCountry(country: String): Iterable<UserOutput> {
        return userSeekService.findAllByCountry(country)
            .map { user -> user.output}
    }

}
