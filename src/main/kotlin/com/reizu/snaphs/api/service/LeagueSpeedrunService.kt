package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.dto.input.LeagueSpeedrun as LeagueSpeedrunInput
import com.reizu.snaphs.api.dto.output.LeagueSpeedrun as LeagueSpeedrunOutput
import com.reizu.snaphs.api.service.seek.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import java.util.concurrent.Future

@Service
class LeagueSpeedrunService {

    @Autowired
    private lateinit var leagueSpeedrunSeekService: LeagueSpeedrunSeekService

    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var speedrunSeekService: SpeedrunSeekService

    @Autowired
    private lateinit var leagueService: LeagueService

    fun create(leagueSpeedrunInput: LeagueSpeedrunInput): LeagueSpeedrunOutput {
        return leagueSpeedrunInput.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)
            val speedrun: Speedrun = speedrunSeekService.find(category, gameName, systemName, isEmulated, region, version)

            // - Add more descriptive error message
            leagueService.validateLeagueChange(league.endedOn)
            val leagueSpeedrun = LeagueSpeedrun(
                league = league,
                speedrun = speedrun
            )
            val createdLeagueSpeedrun: LeagueSpeedrun = leagueSpeedrunSeekService.create(leagueSpeedrun)

            createdLeagueSpeedrun.output
        }
    }

    fun findAll(search: String?): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunSeekService.findAllActive(search = search).map { leagueSpeedrun -> leagueSpeedrun.output }
    }

    @Cacheable("leagueSpeedrun")
    fun findAll(
        leagueName: String?,
        leagueType: LeagueType?,
        category: String?,
        gameName: String?,
        systemName: String?,
        isEmulated: Boolean?,
        region: Region?,
        version: String?
    ): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunSeekService
            .findAll(leagueName, leagueType, category, gameName, systemName, isEmulated, region, version)
            .map { leagueSpeedrun -> leagueSpeedrun.output }
    }

}
