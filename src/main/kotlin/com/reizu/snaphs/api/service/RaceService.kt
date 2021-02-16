package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.dto.input.Race as RaceInput
import com.reizu.snaphs.api.dto.output.Race as RaceOutput
import com.reizu.snaphs.api.entity.Race
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.RaceRunnerSeekService
import com.reizu.snaphs.api.service.seek.RaceSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RaceService {

    @Autowired
    private lateinit var raceSeekService: RaceSeekService

    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @Autowired
    private lateinit var leagueService: LeagueService

    fun create(raceInput: RaceInput): RaceOutput {
        return raceInput.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            leagueService.validateLeagueChange(league.endedOn, "Race $raceName cannot be created - League has ended [End Date: ${league.endedOn}]")

            // If League has ended, disallow races
            val race = Race(
                name = raceName ?: "racename", // ! Replace with name generator
                league = league,
                startedOn = startedOn
            )
            val createdRace: Race = raceSeekService.create(race)

            createdRace.output
        }
    }

    fun findAll(search: String?): Iterable<RaceOutput> {
        return raceSeekService.findAllActive(search = search).map { race -> race.output }
    }

    fun findAllRaces(runnerName: String): List<RaceOutput> {
        return raceRunnerSeekService.findAllByRunner(runnerName).map { raceRunner ->
            raceRunner.race.output
        }
    }

}
