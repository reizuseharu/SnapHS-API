package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.Challenge
import com.reizu.snaphs.api.entity.Console
import com.reizu.snaphs.api.entity.Region
import com.reizu.snaphs.api.entity.Score
import com.reizu.snaphs.api.entity.ScoreAttack
import com.reizu.snaphs.api.entity.User
import com.reizu.snaphs.api.service.seek.ChallengeSeekService
import com.reizu.snaphs.api.service.seek.ScoreAttackSeekService
import com.reizu.snaphs.api.service.seek.UserSeekService
import com.reizu.snaphs.api.dto.input.ScoreAttack as ScoreAttackInput
import com.reizu.snaphs.api.dto.output.ScoreAttack as ScoreAttackOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

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

            val score = Score(
                special = score.special,
                size = score.size,
                pose = score.pose,
                isTechnique = score.isTechnique,
                samePokemon = score.samePokemon,
            )

            val scoreAttack = ScoreAttack(
                user = user,
                challenge = challenge,
                score = score,
                totalScore = score.totalScore,
                console = console,
                region = region,
                picture = picture,
                video = video,
                submittedOn = submittedOn,
                isVerified = isVerified,
                approvedOn = approvedOn
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

    fun validateScoreAttack(id: UUID): ScoreAttackOutput {
        val scoreAttack: ScoreAttack = scoreAttackSeekService.findById(id)
        val modifiedScoreAttack = scoreAttack.copy(isVerified = true)

        scoreAttackSeekService.create(modifiedScoreAttack)

        return modifiedScoreAttack.output
    }

}
