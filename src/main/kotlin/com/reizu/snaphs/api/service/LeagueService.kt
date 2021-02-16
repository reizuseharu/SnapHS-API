package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.input.*
import com.reizu.snaphs.api.dto.output.LeagueSchedule
import com.reizu.snaphs.api.dto.output.Match
import com.reizu.snaphs.api.dto.update.LeagueDivisionShift as LeagueDivisionShiftUpdate
import com.reizu.snaphs.api.dto.input.League as LeagueInput
import com.reizu.snaphs.api.dto.output.League as LeagueOutput
import com.reizu.snaphs.api.dto.search.League as LeagueSearch
import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.entity.LeagueSpeedrun
import com.reizu.snaphs.api.entity.Tier
import com.reizu.snaphs.api.exception.LeagueHasEndedException
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.LeagueSpeedrunSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LeagueService {

    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var playoffService: PlayoffService

    @Autowired
    private lateinit var pointService: PointService

    @Autowired
    private lateinit var seasonService: SeasonService

    @Autowired
    private lateinit var leagueSpeedrunSeekService: LeagueSpeedrunSeekService

    @Autowired
    private lateinit var divisionShiftService: DivisionShiftService

    fun create(leagueInput: LeagueInput): LeagueOutput {
        return leagueInput.run {

            val league = League(
                name = name,
                type = type,
                startedOn = startedOn,
                endedOn = null,
                defaultTime = defaultTime,
                defaultPoints = defaultPoints,
                season = 1,
                tier = Tier(name = tierName, level = 1),
                runnerLimit = runnerLimit,
                registrationEndedOn = registrationEndedOn,
                promotions = promotions,
                relegations = relegations
            )
            val createdLeague: League = leagueSeekService.create(league)

            val leaguePlayoffRule = LeaguePlayoffRule(
                leagueName = name,
                season = createdLeague.season,
                tierLevel = createdLeague.tier.level,
                tierName = createdLeague.tier.name,
                qualifierRules = qualifierRules
            )

            playoffService.addRules(leaguePlayoffRule)

            val leaguePointRule = LeaguePointRule(
                leagueName = name,
                season = 1,
                tierLevel = 1,
                tierName = tierName,
                pointRules = pointRules
            )
            pointService.addRules(leaguePointRule)

            createdLeague.output
        }
    }

    fun findAll(search: String?): Iterable<LeagueOutput> {
        return leagueSeekService.findAllActive(search = search).map { league -> league.output }
    }

    fun endSeason(endSeason: EndSeason): LeagueOutput {
        return endSeason.run {
            val oldLeague: League = leagueSeekService.find(leagueName, season, tierLevel)
            endLeague(oldLeague, endedOn)

            oldLeague.output
        }
    }

    fun startNewSeason(startSeason: StartSeason): List<LeagueOutput> {
        return startSeason.run {
            val allLeagues: List<League> = leagueSeekService.findAllTiers(leagueName, season)
            val newLeagues: List<LeagueOutput> = allLeagues.map { oldLeague ->
                val league = League(
                    name = oldLeague.name,
                    type = oldLeague.type,
                    startedOn = startedOn,
                    endedOn = null,
                    defaultTime = oldLeague.defaultTime,
                    defaultPoints = oldLeague.defaultPoints,
                    season = season + 1,
                    tier = Tier(name = oldLeague.tier.name, level = oldLeague.tier.level),
                    runnerLimit =  oldLeague.runnerLimit,
                    registrationEndedOn = registrationEndedOn
                )
                val createdLeague: League = leagueSeekService.create(league)

                qualifierRules?.let {
                    val leaguePlayoffRule = LeaguePlayoffRule(
                        leagueName = createdLeague.name,
                        season = createdLeague.season,
                        tierLevel = createdLeague.tier.level,
                        tierName = createdLeague.tier.name,
                        qualifierRules = it
                    )

                    playoffService.addRules(leaguePlayoffRule)
                }

                pointRules?.let {
                    val leaguePointRule = LeaguePointRule(
                        leagueName = createdLeague.name,
                        season = createdLeague.season,
                        tierLevel = createdLeague.tier.level,
                        tierName = createdLeague.tier.name,
                        pointRules = it
                    )

                    pointService.addRules(leaguePointRule)
                }

                copySpeedrunsToNewLeague(oldLeague, createdLeague)

                createdLeague.output
            }

            seasonService.shiftDivisions(allLeagues)

            allLeagues.forEach { oldLeague ->
                endLeague(oldLeague, endedOn)
            }

            newLeagues
        }
    }

    fun addLowerTier(lowerTier: LowerTier): LeagueOutput {
        return lowerTier.run {
            val parentLeague: League = leagueSeekService.find(leagueName, season, parentTierLevel)

            // - Add more descriptive error message
            validateLeagueChange(parentLeague.endedOn)

            val league = League(
                name = leagueName,
                type = parentLeague.type,
                startedOn = startedOn,
                endedOn = null,
                defaultTime = defaultTime ?: parentLeague.defaultTime,
                defaultPoints = defaultPoints ?: parentLeague.defaultPoints,
                season = season,
                tier = Tier(name = tierName, level = parentLeague.tier.level + 1),
                runnerLimit = runnerLimit ?: parentLeague.runnerLimit,
                registrationEndedOn = registrationEndedOn,
                promotions = divisionShifts
            )
            val childLeague: League = leagueSeekService.create(league)

            // Update Parent league
            parentLeague.relegations = divisionShifts
            leagueSeekService.create(parentLeague)

            val childQualifierRules: List<QualifierRule> = qualifierRules ?: parentLeague.playoffRules.map { playoffRule ->
                QualifierRule(
                    qualifier = playoffRule.qualifier,
                    count = playoffRule.count
                )
            }
            val leaguePlayoffRule = LeaguePlayoffRule(
                leagueName = childLeague.name,
                season = childLeague.season,
                tierLevel = childLeague.tier.level,
                tierName = childLeague.tier.name,
                qualifierRules = childQualifierRules
            )

            playoffService.addRules(leaguePlayoffRule)

            val childPointRules: List<PointRule> = pointRules ?: parentLeague.pointRules.map { pointRule ->
                PointRule(
                    placement = pointRule.placement,
                    amount = pointRule.amount
                )
            }
            val leaguePointRule = LeaguePointRule(
                leagueName = childLeague.name,
                season = childLeague.season,
                tierLevel = childLeague.tier.level,
                tierName = childLeague.tier.name,
                pointRules = childPointRules
            )

            pointService.addRules(leaguePointRule)

            if (relegationRules != null && promotionRules != null) {

                val leagueRelegationRule = LeagueDivisionShiftRule(
                    leagueName = parentLeague.name,
                    season = parentLeague.season,
                    tierLevel = parentLeague.tier.level,
                    tierName = parentLeague.tier.name,
                    relegationRules = relegationRules
                )

                divisionShiftService.addRelegationRules(leagueRelegationRule)

                val leaguePromotionRule = LeagueDivisionShiftRule(
                    leagueName = childLeague.name,
                    season = childLeague.season,
                    tierLevel = childLeague.tier.level,
                    tierName = childLeague.tier.name,
                    promotionRules = promotionRules
                )

                divisionShiftService.addPromotionRules(leaguePromotionRule)
            }

            copySpeedrunsToNewLeague(parentLeague, childLeague)

            childLeague.output
        }
    }

    fun modifyDivisionShifts(leagueDivisionShift: LeagueDivisionShiftUpdate): LeagueOutput {

        val modifiedLeague: League = updateDivisionShifts(leagueDivisionShift)

        return modifiedLeague.output
    }

    fun generateRoundRobin(leagueSearch: LeagueSearch): LeagueSchedule {
        val matches: List<Match> = seasonService.generateRoundRobin(leagueSearch)

        return leagueSearch.run {
            LeagueSchedule(
                name = name,
                season = season,
                tierLevel = tierLevel,
                matches = matches
            )
        }
    }

    private fun copySpeedrunsToNewLeague(sourceLeague: League, destinationLeague: League) {
        val sourceLeagueSpeedruns: Iterable<LeagueSpeedrun> = sourceLeague.run {
            leagueSpeedrunSeekService.findAllByLeague(name, season, tier.level)
        }

        sourceLeagueSpeedruns.forEach { oldLeagueSpeedrun ->
            val leagueSpeedrun = LeagueSpeedrun(
                league = destinationLeague,
                speedrun = oldLeagueSpeedrun.speedrun
            )

            leagueSpeedrunSeekService.create(leagueSpeedrun)
        }
    }

    fun validateLeagueChange(endedOn: LocalDateTime?, failureMessage: String = "League has ended") {
        // If league has ended, do not allow changes/creation
        endedOn?.run {
            throw LeagueHasEndedException(failureMessage)
        }
    }

    private fun endLeague(league: League, endedOn: LocalDateTime?) {
        validateLeagueChange(league.endedOn)

        league.endedOn ?: run {
            league.endedOn = endedOn
            leagueSeekService.create(league)
        }
    }

    private fun create(entity: League): League {
        val endedOn: LocalDateTime? = entity.endedOn
        validateLeagueChange(endedOn, "League ${entity.name} has ended on [$endedOn] and no changes can be made")

        return leagueSeekService.create(entity)
    }

    private fun updateDivisionShifts(leagueDivisionShift: LeagueDivisionShiftUpdate): League {
        val modifiedLeague: League = leagueDivisionShift.run {
            val mainLeague: League = leagueSeekService.find(leagueName, season, tierLevel)

            promotions?.let {
                val promotedLeague: League = leagueSeekService.find(leagueName, season, tierLevel - 1)
                mainLeague.promotions = promotions
                promotedLeague.relegations = promotions

                create(promotedLeague)
            }

            relegations?.let {
                val relegatedLeague: League = leagueSeekService.find(leagueName, season, tierLevel + 1)
                mainLeague.relegations = relegations
                relegatedLeague.promotions = relegations

                create(relegatedLeague)
            }

            mainLeague
        }

        return create(modifiedLeague)
    }

}
