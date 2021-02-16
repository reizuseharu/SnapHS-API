package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.output.QualifiedRunner
import com.reizu.snaphs.api.dto.output.Standing
import com.reizu.snaphs.api.dto.output.RaceTime as RaceTimeOutput
import com.reizu.snaphs.api.dto.output.Standing as StandingOutput
import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.exception.RaceUnfinishedException
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.RaceRunnerSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StandingService {

    @Autowired
    private lateinit var playoffService: PlayoffService
    
    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    private fun racePoints(pointRules: Set<PointRule>): Map<Int, Int> {
        return pointRules.map { pointRule ->
            pointRule.placement to pointRule.amount
        }.toMap()
    }

    fun calculateStandings(leagueName: String, season: Int, tierLevel: Int): List<Standing> {
        val league: League = leagueSeekService.find(leagueName, season, tierLevel)
        val races: Set<Race> = league.races

        val raceRunners: List<RaceRunner> = races.map { race: Race ->
            raceRunnerSeekService.findAllByRace(race.name)
        }.flatten()

        val raceResults: Map<String, List<RaceRunner>> = raceRunners.groupBy { it.runner.name }

        val pointRules: Set<PointRule> = league.pointRules
        val pointsForPlacement: Map<Int, Int> = racePoints(pointRules)

        val standings: List<Standing> = raceResults.map { (runnerName: String, raceRunners: List<RaceRunner>) ->
            val points: Int = raceRunners.sumBy { raceRunner ->
                val placement: Int? = raceRunner.placement

                placement?.let {
                    pointsForPlacement[placement] ?: league.defaultPoints
                } ?: run {
                    0
                }
            }

            val raceCount: Int = raceRunners.size
            val pointsPerRace: Float = points.toFloat() / raceCount
            val wins: Int = raceRunners.filter { raceRunner -> raceRunner.placement == 1 }.size
            val averageTime: Long = raceRunners.map { raceRunner -> raceRunner.time ?: league.defaultTime }.sum()

            Standing(
                runnerName = runnerName,
                points = points,
                raceCount = raceCount,
                pointsPerRace = pointsPerRace,
                wins = wins,
                averageTime = averageTime
            )
        }

        // ? Possibly have multiple output sorts
        return standings.sortedBy { standing -> standing.points }
    }

    private fun calculateRacePlacements(raceName: String): List<RaceRunner> {
        val raceRunners: List<RaceRunner> = raceRunnerSeekService.findAllByRace(raceName)

        raceRunners.forEach { raceRunner ->
            if (raceRunner.outcome == Outcome.PENDING_VERIFICATION) {
                throw RaceUnfinishedException("Runner [${raceRunner.runner.name}]'s outcome in Race [${raceRunner.race.name}] is still Pending Verification")
            }
        }

        val results: Map<Long, List<RaceRunner>> = raceRunners.groupBy { raceRunner ->
            if (raceRunner.outcome == Outcome.DID_NOT_FINISH) {
                Long.MAX_VALUE
            }
            raceRunner.time ?: throw RaceUnfinishedException("Runner [${raceRunner.runner.name}] does not have a time in Race [${raceRunner.race.name}]")
        }.toSortedMap()

        var placement = 1
        results.forEach { (time: Long, raceRunners: List<RaceRunner>) ->
            if (time == Long.MAX_VALUE) {
                return@forEach
            }

            raceRunners.forEach { raceRunner ->
                raceRunner.placement = placement
                raceRunnerSeekService.create(raceRunner)
            }
            placement += raceRunners.size
        }

        return results.values.flatten()
    }

    fun generateStandings(leagueName: String, season: Int, tierLevel: Int): Iterable<StandingOutput> {
        return calculateStandings(leagueName, season, tierLevel)
    }

    fun findQualifiedRunners(leagueName: String, season: Int, tierLevel: Int, top: Int): Iterable<QualifiedRunner> {
        val standings: List<StandingOutput> = calculateStandings(leagueName, season, tierLevel)
        return playoffService.matchQualifiedRunners(leagueName, season, tierLevel, top, standings)
    }

    fun calculatePlacements(raceName: String): List<RaceTimeOutput> {
        return calculateRacePlacements(raceName).map { raceRunner -> raceRunner.output }
    }

}
