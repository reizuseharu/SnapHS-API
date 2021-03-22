package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.Challenge
import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.ScoreAttack
import com.reizu.snaphs.api.entity.User
import com.reizu.snaphs.api.service.seek.ChallengeSeekService
import com.reizu.snaphs.api.service.seek.ScoreAttackSeekService
import com.reizu.snaphs.api.service.seek.UserSeekService
import com.reizu.snaphs.api.dto.input.ScoreAttack as ScoreAttackInput
import com.reizu.snaphs.api.dto.output.ScoreAttack as ScoreAttackOutput
import com.reizu.snaphs.api.dto.update.ScoreAttack as ScoreAttackUpdate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class ScoreAttackService {

    @Autowired
    private lateinit var scoreAttackSeekService: ScoreAttackSeekService

    @Autowired
    private lateinit var userSeekService: UserSeekService

    @Autowired
    private lateinit var challengeSeekService: ChallengeSeekService

    fun create(scoreAttackInput: ScoreAttackInput): ScoreAttackOutput {
        return scoreAttackInput.run {
            val user: User = userSeekService.findByName(userName)
            val challenge: Challenge = challengeSeekService.findByName(challengeName)

            val scoreAttack = ScoreAttack(
                user = user,
                challenge = challenge,
                special = special,
                size = size,
                pose = pose,
                technique = isTechnique,
                samePokemon = samePokemon,
                totalScore = totalScore,
                console = console,
                region = region,
                proof = proof,
                submittedOn = submittedOn,
                verified = isVerified,
                approvedOn = approvedOn,
                notes = notes
            )
            val createdScoreAttack: ScoreAttack = scoreAttackSeekService.create(scoreAttack)

            createdScoreAttack.output
        }
    }

    fun findAll(search: String?): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllActive(search = search).map { scoreAttack -> scoreAttack.output }
    }

    fun findAllByUser(userName: String): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByUser(userName)
            .map { scoreAttack -> scoreAttack.output}
    }

    fun findAllByChallenge(challengeName: String): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByChallenge(challengeName)
            .map { scoreAttack -> scoreAttack.output}
    }

    fun findAllByPokemon(pokemonName: String): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByPokemon(pokemonName)
            .map { scoreAttack -> scoreAttack.output}
    }

    fun findAllByConsole(console: Console): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByConsole(console)
            .map { scoreAttack -> scoreAttack.output}
    }

    fun findAllByRegion(region: Region): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByRegion(region)
            .map { scoreAttack -> scoreAttack.output}
    }

    fun findAllOrdered(): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllOrdered()
            .map { scoreAttack -> scoreAttack.output}
    }

    fun findAllScoreOrdered(): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllOrdered()
            .groupBy { scoreAttack -> Pair(scoreAttack.user.name, scoreAttack.challenge.name) }
            .map { (_, scoreAttacks) -> scoreAttacks.first().output }
    }

    fun findAllScoreByPokemon(pokemonName: String): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByPokemon(pokemonName)
            .groupBy { scoreAttack -> Pair(scoreAttack.user.name, scoreAttack.challenge.name) }
            .map { (_, scoreAttacks) -> scoreAttacks.first().output }
    }

    fun findAllScoreByChallenge(challengeName: String): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByChallenge(challengeName)
            .groupBy { scoreAttack -> Pair(scoreAttack.user.name, scoreAttack.challenge.name) }
            .map { (_, scoreAttacks) -> scoreAttacks.first().output }
    }

    fun findAllScoreByConsole(console: Console): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByConsole(console)
            .groupBy { scoreAttack -> Pair(scoreAttack.user.name, scoreAttack.challenge.name) }
            .map { (_, scoreAttacks) -> scoreAttacks.first().output }
    }

    fun findAllScoreByRegion(region: Region): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByRegion(region)
            .groupBy { scoreAttack -> Pair(scoreAttack.user.name, scoreAttack.challenge.name) }
            .map { (_, scoreAttacks) -> scoreAttacks.first().output }
    }

    fun findAllByChallengeAndConsoleVerified(challengeName: String, console: Console): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByChallengeAndConsoleVerified(challengeName, console)
            .groupBy { scoreAttack -> Pair(scoreAttack.user.name, scoreAttack.challenge.name) }
            .map { (_, scoreAttacks) -> scoreAttacks.first().output }
    }

    fun findAllByChallengeAndConsoleNotVerified(challengeName: String, console: Console): Iterable<ScoreAttackOutput> {
        return scoreAttackSeekService.findAllByChallengeAndConsoleNotVerified(challengeName, console)
            .groupBy { scoreAttack -> Pair(scoreAttack.user.name, scoreAttack.challenge.name) }
            .map { (_, scoreAttacks) -> scoreAttacks.first().output }
    }

    fun validateScoreAttack(scoreAttackUpdate: ScoreAttackUpdate): ScoreAttackOutput {
        return scoreAttackUpdate.run {
            validateUser(userName, password)

            val scoreAttack: ScoreAttack = scoreAttackSeekService.findById(id)
            val modifiedScoreAttack = scoreAttack.copy(verified = true)

            scoreAttackSeekService.create(modifiedScoreAttack)

            modifiedScoreAttack.output
        }
    }

    private fun validateUser(userName: String, password: String) {
        // Validate User
        val user: User = userSeekService.findByName(userName)
        // - Move into Verify Service
        user.run {
            val messageDigest = MessageDigest.getInstance("SHA-512")
            messageDigest.update(salt.fromHexToByteArray())
            val inputHashedPassword = messageDigest.digest(password.toByteArray())
            val storedHashedPassword = hashedPassword.fromHexToByteArray()

            if (!inputHashedPassword.contentEquals(storedHashedPassword)) {
                throw RuntimeException("Incorrect password")
            }
        }
    }

}
