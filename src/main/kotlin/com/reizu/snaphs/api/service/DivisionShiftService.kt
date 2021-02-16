package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.input.LeagueDivisionShiftRule as LeagueDivisionShiftRuleInput
import com.reizu.snaphs.api.dto.input.QualifierRule
import com.reizu.snaphs.api.dto.output.DivisionShiftRule as DivisionShiftRuleOutput
import com.reizu.snaphs.api.dto.output.QualifiedRunner
import com.reizu.snaphs.api.dto.output.Standing
import com.reizu.snaphs.api.entity.League
import com.reizu.snaphs.api.entity.DivisionShiftRule
import com.reizu.snaphs.api.entity.Qualifier
import com.reizu.snaphs.api.entity.Shift
import com.reizu.snaphs.api.service.seek.DivisionShiftRuleSeekService
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class DivisionShiftService {

    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var divisionShiftRuleSeekService: DivisionShiftRuleSeekService

    private fun attachDivisionShiftRules(league: League, qualifierRules: List<QualifierRule>, shift: Shift): List<DivisionShiftRule> {
        return qualifierRules.mapIndexed { index, qualifierRule ->
            val divisionShiftRule = DivisionShiftRule(
                qualifier = qualifierRule.qualifier,
                count = qualifierRule.count,
                shift = shift,
                league = league,
                order = index + 1
            )

            divisionShiftRuleSeekService.create(divisionShiftRule)
        }
    }

    private fun attachDivisionShiftRules(league: League, leagueRule: LeagueDivisionShiftRuleInput): List<DivisionShiftRule> {
        val promotionRules: List<DivisionShiftRule> = attachDivisionShiftRules(league, leagueRule.promotionRules, Shift.PROMOTION)
        val relegationRules: List<DivisionShiftRule> = attachDivisionShiftRules(league, leagueRule.relegationRules, Shift.RELEGATION)

        return promotionRules.union(relegationRules).toList()
    }

    fun addDivisionShiftRules(leagueRule: LeagueDivisionShiftRuleInput): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueService.validateLeagueChange(league.endedOn)

            attachDivisionShiftRules(league, leagueRule)
        }
    }

    fun addPromotionRules(leagueRule: LeagueDivisionShiftRuleInput): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueService.validateLeagueChange(league.endedOn)

            attachDivisionShiftRules(league, leagueRule.promotionRules, Shift.PROMOTION)
        }
    }

    fun addRelegationRules(leagueRule: LeagueDivisionShiftRuleInput): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueService.validateLeagueChange(league.endedOn)

            attachDivisionShiftRules(league, leagueRule.relegationRules, Shift.RELEGATION)
        }
    }

    private fun substituteDivisionShiftRules(leagueRule: LeagueDivisionShiftRuleInput): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueService.validateLeagueChange(league.endedOn)

            divisionShiftRuleSeekService.deleteAll(league.divisionShiftRules)

            attachDivisionShiftRules(league, leagueRule)
        }
    }

    private fun substitutePromotionRules(leagueRule: LeagueDivisionShiftRuleInput): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueService.validateLeagueChange(league.endedOn)

            divisionShiftRuleSeekService.deleteAll(league.promotionRules)

            attachDivisionShiftRules(league, leagueRule.promotionRules, Shift.PROMOTION)
        }
    }

    private fun substituteRelegationRules(leagueRule: LeagueDivisionShiftRuleInput): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueService.validateLeagueChange(league.endedOn)

            divisionShiftRuleSeekService.deleteAll(league.relegationRules)

            attachDivisionShiftRules(league, leagueRule.relegationRules, Shift.RELEGATION)
        }
    }

    private fun findQualifiedRunners(top: Int, standings: List<Standing>, shift: Shift): MutableMap<Qualifier, List<QualifiedRunner>> {
        return when(shift) {
            Shift.PROMOTION -> {
                mutableMapOf(
                    Qualifier.POINTS to standings.sortedByDescending { it.points }.take(top),
                    Qualifier.POINTS_PER_RACE to standings.sortedByDescending { it.pointsPerRace }.take(top),
                    Qualifier.WINS to standings.sortedByDescending { it.wins }.take(top),
                    Qualifier.AVERAGE_TIME to standings.sortedByDescending { it.averageTime }.take(top)
                )
            }
            Shift.RELEGATION -> {
                mutableMapOf(
                    Qualifier.POINTS to standings.sortedBy { it.points }.take(top),
                    Qualifier.POINTS_PER_RACE to standings.sortedBy { it.pointsPerRace }.take(top),
                    Qualifier.WINS to standings.sortedBy { it.wins }.take(top),
                    Qualifier.AVERAGE_TIME to standings.sortedBy { it.averageTime }.take(top)
                )
            }
        }
    }

    fun matchQualifiedRunners(leagueName: String, season: Int, tierLevel: Int, standings: List<Standing>, shift: Shift): List<QualifiedRunner> {
        val league: League = leagueSeekService.find(leagueName, season, tierLevel)
        val qualifiedRunners: LinkedHashSet<QualifiedRunner> = linkedSetOf()

        val top: Int = when(shift) {
            Shift.PROMOTION -> league.promotions
            Shift.RELEGATION -> league.relegations
        }
        val topRunners: MutableMap<Qualifier, List<QualifiedRunner>> = findQualifiedRunners(top, standings, shift)
        val orderedDivisionShiftRules: List<DivisionShiftRule> = when(shift) {
            Shift.PROMOTION -> league.promotionRules.sortedBy { it.order }
            Shift.RELEGATION -> league.relegationRules.sortedBy { it.order }
        }

        // ! There might be an issue with infinite loop if less runners than top
        while (qualifiedRunners.size < top) {
            orderedDivisionShiftRules.forEach { divisionShiftRule ->
                val qualifier: Qualifier = divisionShiftRule.qualifier
                val possiblyQualifiedRunners: List<QualifiedRunner> = topRunners[qualifier]
                    ?: throw IllegalStateException("Each Qualifier should have a list of possible runners")
                // - Consider outputting each qualification method
                val orderedQualifiedRunners = possiblyQualifiedRunners.take(divisionShiftRule.count)

                qualifiedRunners.addAll(orderedQualifiedRunners)
                topRunners[qualifier] = possiblyQualifiedRunners.drop(divisionShiftRule.count)
            }
        }

        return qualifiedRunners.take(top)

    }

    fun create(leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return addDivisionShiftRules(leagueDivisionShiftRuleInput).map { divisionShiftRule ->
            divisionShiftRule.output
        }.sortedBy { it.order }
    }

    fun findAll(search: String?): Iterable<DivisionShiftRuleOutput> {
        return divisionShiftRuleSeekService.findAllActive(search = search).map { divisionShiftRule ->
            divisionShiftRule.output
        }.sortedBy { it.order }
    }

    fun findAll(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRuleOutput> {
        return divisionShiftRuleSeekService.findAllByLeague(leagueName, season, tierLevel).map { divisionShiftRule ->
            divisionShiftRule.output
        }.sortedBy { it.order }
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return substituteDivisionShiftRules(leagueDivisionShiftRuleInput).map { divisionShiftRule ->
            divisionShiftRule.output
        }.sortedBy { it.order }
    }

    fun createPromotionRules(leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return addPromotionRules(leagueDivisionShiftRuleInput).map { promotionRule ->
            promotionRule.output
        }.sortedBy { it.order }
    }

    fun findAllPromotionRules(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRuleOutput> {
        return divisionShiftRuleSeekService.findAllPromotionByLeague(leagueName, season, tierLevel).map { promotionRule ->
            promotionRule.output
        }.sortedBy { it.order }
    }

    fun replacePromotionRules(leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return substitutePromotionRules(leagueDivisionShiftRuleInput).map { promotionRule ->
            promotionRule.output
        }.sortedBy { it.order }
    }

    fun createRelegationRules(leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return addRelegationRules(leagueDivisionShiftRuleInput).map { relegationRule ->
            relegationRule.output
        }.sortedBy { it.order }
    }

    fun findAllRelegationRules(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRuleOutput> {
        return divisionShiftRuleSeekService.findAllRelegationByLeague(leagueName, season, tierLevel).map { relegationRule ->
            relegationRule.output
        }.sortedBy { it.order }
    }

    fun replaceRelegationRules(leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return substituteRelegationRules(leagueDivisionShiftRuleInput).map { relegationRule ->
            relegationRule.output
        }.sortedBy { it.order }
    }

}
