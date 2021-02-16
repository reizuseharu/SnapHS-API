package com.reizu.snaphs.api.service

import com.google.common.collect.Sets
import com.reizu.snaphs.api.dto.output.Match
import com.reizu.snaphs.api.dto.output.QualifiedRunner
import com.reizu.snaphs.api.dto.output.Standing
import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.dto.search.League as LeagueSearch
import com.reizu.snaphs.api.service.seek.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SeasonService {
    
    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @Autowired
    private lateinit var runnerSeekService: RunnerSeekService

    @Autowired
    private lateinit var raceSeekService: RaceSeekService

    @Autowired
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var standingService: StandingService

    @Autowired
    private lateinit var divisionShiftService: DivisionShiftService

    fun shiftDivisions(leagues: List<League>) {
        leagues.forEach { league ->
            // - Add more descriptive error message
            leagueService.validateLeagueChange(league.endedOn)

            val standings: List<Standing> = standingService.calculateStandings(league.name, league.season, league.tier.level)

            val relegatedRunners: List<QualifiedRunner> = divisionShiftService.matchQualifiedRunners(league.name, league.season, league.tier.level, standings, Shift.RELEGATION)
            if (relegatedRunners.isNotEmpty()) {
                val relegatedLeague: League = leagueSeekService.find(league.name, league.season + 1, league.tier.level + 1)

                relegatedRunners.forEach { relegatedRunner ->
                    val leagueRunner = LeagueRunner(
                        league = relegatedLeague,
                        runner = runnerSeekService.findByName(relegatedRunner.runnerName)
                    )

                    leagueRunnerSeekService.create(leagueRunner)
                }
            }

            val promotedRunners: List<QualifiedRunner> = divisionShiftService.matchQualifiedRunners(league.name, league.season, league.tier.level, standings, Shift.PROMOTION)
            if (promotedRunners.isNotEmpty()) {
                val promotedLeague: League = leagueSeekService.find(league.name, league.season + 1, league.tier.level - 1)

                promotedRunners.forEach { promotedRunner ->
                    val leagueRunner = LeagueRunner(
                        league = promotedLeague,
                        runner = runnerSeekService.findByName(promotedRunner.runnerName)
                    )

                    leagueRunnerSeekService.create(leagueRunner)
                }
            }

            val relegatedRunnerNames: Set<String> = relegatedRunners.map { relegatedRunner -> relegatedRunner.runnerName }.toSet()
            val promotedRunnerNames: Set<String> = promotedRunners.map { promotedRunner -> promotedRunner.runnerName }.toSet()
            val leagueRunners: Set<LeagueRunner> = leagueRunnerSeekService.findAllByLeague(league.name, league.season, league.tier.level).toSet()

            leagueRunners.forEach { leagueRunner ->
                if (!relegatedRunnerNames.contains(leagueRunner.runner.name) && !promotedRunnerNames.contains(leagueRunner.runner.name)) {
                    val newLeague: League = leagueSeekService.find(league.name, league.season + 1, league.tier.level)
                    val newLeagueRunner = LeagueRunner(
                        league = newLeague,
                        runner = leagueRunner.runner
                    )
                    leagueRunnerSeekService.create(newLeagueRunner)
                }
            }
        }
    }

    fun generateRoundRobin(leagueSearch: LeagueSearch): List<Match> {
        val league: League = leagueSearch.run {
            leagueSeekService.find(name, season, tierLevel)
        }
        leagueService.validateLeagueChange(league.endedOn)

        val runnersInLeague: Set<Runner> = leagueSearch.run {
            leagueRunnerSeekService.findAllByLeague(name, season, tierLevel)
        }.map { leagueRunner -> leagueRunner.runner }.toSet()

        val versus: Set<Set<Runner>> = Sets.combinations(runnersInLeague, 2)

        // ! Fix race start dates
        val startedOn: LocalDateTime = LocalDateTime.now()

        // ! Replace with Race name generation
        var raceCount = 1
        return versus.map { contestants ->
            val leagueContestants = contestants.toList()

            val homeRunner: Runner = leagueContestants[0]
            val awayRunner: Runner = leagueContestants[1]

            val race = leagueSearch.run {
                Race(
                    name = "$name-$season-$tierLevel-RoundRobin-$raceCount",
                    league = league,
                    startedOn = startedOn
                )
            }


            val createdRace: Race = raceSeekService.create(race)
            val homeRaceRunner = RaceRunner(
                race = createdRace,
                runner = homeRunner
            )
            val awayRaceRunner = RaceRunner(
                race = createdRace,
                runner = awayRunner
            )

            raceRunnerSeekService.create(homeRaceRunner)
            raceRunnerSeekService.create(awayRaceRunner)

            raceCount += 1

            Match(
                race = createdRace.name,
                homeRunner = homeRunner.name,
                awayRunner = awayRunner.name,
                startedOn = createdRace.startedOn
            )
        }

    }

}
