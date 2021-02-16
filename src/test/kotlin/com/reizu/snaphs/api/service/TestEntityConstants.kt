package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.input.EndSeason
import com.reizu.snaphs.api.dto.input.LowerTier
import com.reizu.snaphs.api.dto.input.QualifierRule
import com.reizu.snaphs.api.dto.input.StartSeason
import com.reizu.snaphs.api.dto.output.LeagueSchedule
import com.reizu.snaphs.api.dto.output.Match
import com.reizu.snaphs.api.dto.output.Standing
import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.dto.input.Cart as CartInput
import com.reizu.snaphs.api.dto.input.Game as GameInput
import com.reizu.snaphs.api.dto.input.League as LeagueInput
import com.reizu.snaphs.api.dto.input.LeagueDivisionShiftRule as LeagueDivisionShiftRuleInput
import com.reizu.snaphs.api.dto.input.LeaguePlayoffRule as LeaguePlayoffRuleInput
import com.reizu.snaphs.api.dto.input.LeaguePointRule as LeaguePointRuleInput
import com.reizu.snaphs.api.dto.input.LeagueRunner as LeagueRunnerInput
import com.reizu.snaphs.api.dto.input.LeagueSpeedrun as LeagueSpeedrunInput
import com.reizu.snaphs.api.dto.input.PointRule as PointRuleInput
import com.reizu.snaphs.api.dto.input.Race as RaceInput
import com.reizu.snaphs.api.dto.input.RaceTime as RaceTimeInput
import com.reizu.snaphs.api.dto.input.Runner as RunnerInput
import com.reizu.snaphs.api.dto.input.Speedrun as SpeedrunInput
import com.reizu.snaphs.api.dto.input.System as SystemInput
import com.reizu.snaphs.api.dto.search.League as LeagueSearch
import com.reizu.snaphs.api.dto.update.RaceTime as RaceTimeRegister
import com.reizu.snaphs.api.dto.update.LeagueDivisionShift as LeagueDivisionShiftUpdate
import com.reizu.snaphs.api.service.TestConstants as C

object TestEntityConstants {

    val VALID_TIER: Tier = Tier(
        name = C.VALID_TIER_NAME,
        level = C.VALID_TIER_LEVEL
    )

    val INVALID_TIER: Tier = Tier(
        name = C.INVALID_TIER_NAME,
        level = C.INVALID_TIER_LEVEL
    )

    val VALID_GAME_INPUT: GameInput = GameInput(
        name = C.VALID_GAME_NAME,
        shorthand = C.VALID_SHORTHAND
    )

    val INVALID_GAME_INPUT: GameInput = GameInput(
        name = C.INVALID_GAME_NAME,
        shorthand = C.INVALID_SHORTHAND
    )

    val VALID_GAME: Game = Game(
        name = C.VALID_GAME_NAME,
        shorthand = C.VALID_SHORTHAND
    )

    val INVALID_GAME: Game = Game(
        name = C.INVALID_GAME_NAME,
        shorthand = C.INVALID_SHORTHAND
    )

    val VALID_SYSTEM_INPUT: SystemInput = SystemInput(
        name = C.VALID_SYSTEM_NAME,
        isEmulated = C.VALID_IS_EMULATED
    )

    val INVALID_SYSTEM_INPUT: SystemInput = SystemInput(
        name = C.INVALID_SYSTEM_NAME,
        isEmulated = C.INVALID_IS_EMULATED
    )

    val VALID_SYSTEM: System = System(
        name = C.VALID_SYSTEM_NAME,
        isEmulated = C.VALID_IS_EMULATED
    )

    val INVALID_SYSTEM: System = System(
        name = C.INVALID_SYSTEM_NAME,
        isEmulated = C.INVALID_IS_EMULATED
    )

    val VALID_LEAGUE: League = League(
        name = C.VALID_LEAGUE_NAME,
        type = C.VALID_LEAGUE_TYPE,
        startedOn = C.VALID_STARTED_ON,
        endedOn = C.VALID_ENDED_ON,
        defaultTime = C.VALID_DEFAULT_TIME,
        defaultPoints = C.VALID_DEFAULT_POINTS,
        season = C.VALID_SEASON,
        tier = VALID_TIER,
        runnerLimit = C.VALID_RUNNER_LIMIT,
        promotions = C.VALID_PROMOTIONS,
        relegations = C.VALID_RELEGATIONS
    )

    val INVALID_LEAGUE: League = League(
        name = C.INVALID_LEAGUE_NAME,
        type = C.INVALID_LEAGUE_TYPE,
        startedOn = C.INVALID_STARTED_ON,
        endedOn = C.INVALID_ENDED_ON,
        defaultTime = C.INVALID_DEFAULT_TIME,
        defaultPoints = C.INVALID_DEFAULT_POINTS,
        season = C.INVALID_SEASON,
        tier = INVALID_TIER,
        runnerLimit = C.INVALID_RUNNER_LIMIT,
        promotions = C.INVALID_PROMOTIONS,
        relegations = C.INVALID_RELEGATIONS
    )

    val VALID_RACE_INPUT: RaceInput = RaceInput(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL,
        raceName = C.VALID_RACE_NAME,
        startedOn = C.VALID_STARTED_ON
    )

    val INVALID_RACE_INPUT: RaceInput = RaceInput(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL,
        raceName = C.INVALID_RACE_NAME,
        startedOn = C.INVALID_STARTED_ON
    )

    val VALID_RACE: Race = Race(
        name = C.VALID_RACE_NAME,
        league = VALID_LEAGUE,
        startedOn = C.VALID_STARTED_ON
    )

    val INVALID_RACE: Race = Race(
        name = C.INVALID_RACE_NAME,
        league = INVALID_LEAGUE,
        startedOn = C.INVALID_STARTED_ON
    )

    val VALID_RUNNER_INPUT: RunnerInput = RunnerInput(
        name = C.VALID_RUNNER_NAME,
        joinedOn = C.VALID_JOINED_ON
    )

    val INVALID_RUNNER_INPUT: RunnerInput = RunnerInput(
        name = C.INVALID_RUNNER_NAME,
        joinedOn = C.INVALID_JOINED_ON
    )

    val VALID_RUNNER: Runner = Runner(
        name = C.VALID_RUNNER_NAME,
        joinedOn = C.VALID_JOINED_ON
    )

    val INVALID_RUNNER: Runner = Runner(
        name = C.INVALID_RUNNER_NAME,
        joinedOn = C.INVALID_JOINED_ON
    )

    val VALID_RACE_RUNNER: RaceRunner = RaceRunner(
        race = VALID_RACE,
        runner = VALID_RUNNER,
        time = C.VALID_RACE_RUNNER_TIME,
        outcome = C.VALID_OUTCOME,
        placement = C.VALID_PLACEMENT,
        joinedOn = C.VALID_JOINED_ON
    )

    val INVALID_RACE_RUNNER: RaceRunner = RaceRunner(
        race = INVALID_RACE,
        runner = INVALID_RUNNER,
        time = C.INVALID_RACE_RUNNER_TIME,
        outcome = C.INVALID_OUTCOME,
        placement = C.INVALID_PLACEMENT,
        joinedOn = C.INVALID_JOINED_ON
    )

    val VALID_LEAGUE_RUNNER_INPUT: LeagueRunnerInput = LeagueRunnerInput(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        runnerName = C.VALID_RUNNER_NAME,
        joinedOn = C.VALID_JOINED_ON
    )

    val INVALID_LEAGUE_RUNNER_INPUT: LeagueRunnerInput = LeagueRunnerInput(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        runnerName = C.INVALID_RUNNER_NAME,
        joinedOn = C.INVALID_JOINED_ON
    )

    val VALID_LEAGUE_RUNNER: LeagueRunner = LeagueRunner(
        league = VALID_LEAGUE,
        runner = VALID_RUNNER,
        joinedOn = C.VALID_JOINED_ON
    )

    val INVALID_LEAGUE_RUNNER: LeagueRunner = LeagueRunner(
        league = INVALID_LEAGUE,
        runner = INVALID_RUNNER,
        joinedOn = C.INVALID_JOINED_ON
    )

    val VALID_RACE_TIME_INPUT: RaceTimeInput = RaceTimeInput(
        runnerName = C.VALID_RUNNER_NAME,
        raceName = C.VALID_RACE_NAME,
        time = C.VALID_RACE_RUNNER_TIME,
        outcome = C.VALID_OUTCOME,
        joinedOn = C.VALID_JOINED_ON
    )

    val INVALID_RACE_TIME_INPUT: RaceTimeInput = RaceTimeInput(
        runnerName = C.INVALID_RUNNER_NAME,
        raceName = C.INVALID_RACE_NAME,
        time = C.INVALID_RACE_RUNNER_TIME,
        outcome = C.INVALID_OUTCOME,
        joinedOn = C.INVALID_JOINED_ON
    )

    val VALID_RACE_TIME_REGISTER: RaceTimeRegister = RaceTimeRegister(
        runnerName = C.VALID_RUNNER_NAME,
        raceName = C.VALID_RACE_NAME,
        time = C.VALID_RACE_RUNNER_TIME,
        outcome = C.VALID_OUTCOME
    )

    val INVALID_RACE_TIME_REGISTER: RaceTimeRegister = RaceTimeRegister(
        runnerName = C.INVALID_RUNNER_NAME,
        raceName = C.INVALID_RACE_NAME,
        time = C.INVALID_RACE_RUNNER_TIME,
        outcome = C.INVALID_OUTCOME
    )
    
    val VALID_CART: Cart = Cart(
        game = VALID_GAME,
        system = VALID_SYSTEM,
        region = C.VALID_REGION,
        version = C.VALID_VERSION
    )

    val INVALID_CART: Cart = Cart(
        game = INVALID_GAME,
        system = INVALID_SYSTEM,
        region = C.INVALID_REGION,
        version = C.INVALID_VERSION
    )
    
    val VALID_CART_INPUT: CartInput = CartInput(
        gameName = C.VALID_GAME_NAME,
        systemName = C.VALID_SYSTEM_NAME,
        isEmulated = C.VALID_IS_EMULATED,
        region = C.VALID_REGION,
        version = C.VALID_VERSION
    )

    val INVALID_CART_INPUT: CartInput = CartInput(
        gameName = C.INVALID_GAME_NAME,
        systemName = C.INVALID_SYSTEM_NAME,
        isEmulated = C.INVALID_IS_EMULATED,
        region = C.INVALID_REGION,
        version = C.INVALID_VERSION
    )
    
    val VALID_SPEEDRUN: Speedrun = Speedrun(
        cart = VALID_CART,
        category = C.VALID_CATEGORY
    )

    val INVALID_SPEEDRUN: Speedrun = Speedrun(
        cart = INVALID_CART,
        category = C.INVALID_CATEGORY
    )
    
    val VALID_SPEEDRUN_INPUT: SpeedrunInput = SpeedrunInput(
        category = C.VALID_CATEGORY,
        gameName = C.VALID_GAME_NAME,
        systemName = C.VALID_SYSTEM_NAME,
        isEmulated = C.VALID_IS_EMULATED,
        region = C.VALID_REGION,
        version = C.VALID_VERSION
    )

    val INVALID_SPEEDRUN_INPUT: SpeedrunInput = SpeedrunInput(
        category = C.INVALID_CATEGORY,
        gameName = C.INVALID_GAME_NAME,
        systemName = C.INVALID_SYSTEM_NAME,
        isEmulated = C.INVALID_IS_EMULATED,
        region = C.INVALID_REGION,
        version = C.INVALID_VERSION
    )

    val VALID_LEAGUE_SPEEDRUN_INPUT: LeagueSpeedrunInput = LeagueSpeedrunInput(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL,
        category = C.VALID_CATEGORY,
        gameName = C.VALID_GAME_NAME,
        systemName = C.VALID_SYSTEM_NAME,
        isEmulated = C.VALID_IS_EMULATED,
        region = C.VALID_REGION,
        version = C.VALID_VERSION
    )

    val INVALID_LEAGUE_SPEEDRUN_INPUT: LeagueSpeedrunInput = LeagueSpeedrunInput(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL,
        category = C.INVALID_CATEGORY,
        gameName = C.INVALID_GAME_NAME,
        systemName = C.INVALID_SYSTEM_NAME,
        isEmulated = C.INVALID_IS_EMULATED,
        region = C.INVALID_REGION,
        version = C.INVALID_VERSION
    )

    val VALID_LEAGUE_SPEEDRUN: LeagueSpeedrun = LeagueSpeedrun(
        league = VALID_LEAGUE,
        speedrun = VALID_SPEEDRUN
    )

    val INVALID_LEAGUE_SPEEDRUN: LeagueSpeedrun = LeagueSpeedrun(
        league = INVALID_LEAGUE,
        speedrun = INVALID_SPEEDRUN
    )
    
    val VALID_STANDING: Standing = Standing(
        runnerName = C.VALID_RUNNER_NAME,
        points = C.VALID_POINTS,
        pointsPerRace = C.VALID_POINTS_PER_RACE,
        raceCount = C.VALID_RACE_COUNT,
        wins = C.VALID_WINS,
        averageTime = C.VALID_AVERAGE_TIME
    )

    val INVALID_STANDING: Standing = Standing(
        runnerName = C.INVALID_RUNNER_NAME,
        points = C.INVALID_POINTS,
        pointsPerRace = C.INVALID_POINTS_PER_RACE,
        raceCount = C.INVALID_RACE_COUNT,
        wins = C.INVALID_WINS,
        averageTime = C.INVALID_AVERAGE_TIME
    )

    val VALID_POINT_RULE_INPUT: PointRuleInput = PointRuleInput(
        placement = C.VALID_PLACEMENT!!,
        amount = C.VALID_AMOUNT
    )

    val INVALID_POINT_RULE_INPUT: PointRuleInput = PointRuleInput(
        placement = C.INVALID_PLACEMENT!!,
        amount = C.INVALID_AMOUNT
    )

    val VALID_POINT_RULE: PointRule = PointRule(
        placement = C.VALID_PLACEMENT!!,
        amount = C.VALID_AMOUNT,
        league = VALID_LEAGUE,
        addedOn = C.VALID_ADDED_ON
    )

    val INVALID_POINT_RULE: PointRule = PointRule(
        placement = C.INVALID_PLACEMENT!!,
        amount = C.INVALID_AMOUNT,
        league = INVALID_LEAGUE,
        addedOn = C.INVALID_ADDED_ON
    )

    val VALID_LEAGUE_POINT_RULE_INPUT: LeaguePointRuleInput = LeaguePointRuleInput(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL,
        tierName = C.VALID_TIER_NAME,
        pointRules = listOf(VALID_POINT_RULE_INPUT)
    )

    val INVALID_LEAGUE_POINT_RULE_INPUT: LeaguePointRuleInput = LeaguePointRuleInput(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL,
        tierName = C.INVALID_TIER_NAME,
        pointRules = listOf(INVALID_POINT_RULE_INPUT)
    )
    
    val VALID_QUALIFIER_RULE: QualifierRule = QualifierRule(
        qualifier = C.VALID_QUALIFIER,
        count = C.VALID_RACE_COUNT
    )

    val INVALID_QUALIFIER_RULE: QualifierRule = QualifierRule(
        qualifier = C.INVALID_QUALIFIER,
        count = C.INVALID_RACE_COUNT
    )

    val VALID_PLAYOFF_RULE: PlayoffRule = PlayoffRule(
        qualifier = C.VALID_QUALIFIER,
        count = C.VALID_RACE_COUNT,
        league = VALID_LEAGUE,
        addedOn = C.VALID_ADDED_ON,
        order = C.VALID_ORDER
    )

    val INVALID_PLAYOFF_RULE: PlayoffRule = PlayoffRule(
        qualifier = C.INVALID_QUALIFIER,
        count = C.INVALID_RACE_COUNT,
        league = INVALID_LEAGUE,
        addedOn = C.INVALID_ADDED_ON,
        order = C.INVALID_ORDER
    )

    val VALID_LEAGUE_PLAYOFF_RULE_INPUT: LeaguePlayoffRuleInput = LeaguePlayoffRuleInput(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL,
        tierName = C.VALID_TIER_NAME,
        qualifierRules = listOf(VALID_QUALIFIER_RULE)
    )

    val INVALID_LEAGUE_PLAYOFF_RULE_INPUT: LeaguePlayoffRuleInput = LeaguePlayoffRuleInput(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL,
        tierName = C.INVALID_TIER_NAME,
        qualifierRules = listOf(INVALID_QUALIFIER_RULE)
    )

    val VALID_LEAGUE_DIVISION_SHIFT_RULE_INPUT: LeagueDivisionShiftRuleInput = LeagueDivisionShiftRuleInput(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL,
        tierName = C.VALID_TIER_NAME,
        promotionRules = listOf(VALID_QUALIFIER_RULE),
        relegationRules = listOf(VALID_QUALIFIER_RULE)
    )

    val INVALID_LEAGUE_DIVISION_SHIFT_RULE_INPUT: LeagueDivisionShiftRuleInput = LeagueDivisionShiftRuleInput(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL,
        tierName = C.INVALID_TIER_NAME,
        promotionRules = listOf(INVALID_QUALIFIER_RULE),
        relegationRules = listOf(INVALID_QUALIFIER_RULE)
    )

    val VALID_DIVISION_SHIFT_RULE: DivisionShiftRule = DivisionShiftRule(
        qualifier = C.VALID_QUALIFIER,
        count = C.VALID_RACE_COUNT,
        shift = C.VALID_SHIFT,
        league = VALID_LEAGUE,
        order = C.VALID_ORDER
    )

    val INVALID_DIVISION_SHIFT_RULE: DivisionShiftRule = DivisionShiftRule(
        qualifier = C.INVALID_QUALIFIER,
        count = C.INVALID_RACE_COUNT,
        shift = C.INVALID_SHIFT,
        league = INVALID_LEAGUE,
        order = C.INVALID_ORDER
    )

    val VALID_LEAGUE_INPUT: LeagueInput = LeagueInput(
        name = C.VALID_LEAGUE_NAME,
        type = C.VALID_LEAGUE_TYPE,
        startedOn = C.VALID_STARTED_ON,
        defaultTime = C.VALID_DEFAULT_TIME,
        defaultPoints = C.VALID_DEFAULT_POINTS,
        tierName = C.VALID_TIER_NAME,
        runnerLimit = C.VALID_RUNNER_LIMIT,
        promotions = C.VALID_PROMOTIONS,
        relegations = C.VALID_RELEGATIONS,
        qualifierRules = listOf(VALID_QUALIFIER_RULE),
        pointRules = listOf(VALID_POINT_RULE_INPUT),
        promotionRules = listOf(VALID_QUALIFIER_RULE),
        relegationRules = listOf(VALID_QUALIFIER_RULE)
    )

    val INVALID_LEAGUE_INPUT: LeagueInput = LeagueInput(
        name = C.INVALID_LEAGUE_NAME,
        type = C.INVALID_LEAGUE_TYPE,
        startedOn = C.INVALID_STARTED_ON,
        defaultTime = C.INVALID_DEFAULT_TIME,
        defaultPoints = C.INVALID_DEFAULT_POINTS,
        tierName = C.INVALID_TIER_NAME,
        runnerLimit = C.INVALID_RUNNER_LIMIT,
        promotions = C.INVALID_PROMOTIONS,
        relegations = C.INVALID_RELEGATIONS,
        qualifierRules = listOf(INVALID_QUALIFIER_RULE),
        pointRules = listOf(INVALID_POINT_RULE_INPUT),
        promotionRules = listOf(INVALID_QUALIFIER_RULE),
        relegationRules = listOf(INVALID_QUALIFIER_RULE)
    )
    
    val VALID_END_SEASON: EndSeason = EndSeason(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL
    )

    val INVALID_END_SEASON: EndSeason = EndSeason(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL,
        endedOn = C.INVALID_ENDED_ON!!
    )

    val VALID_START_SEASON: StartSeason = StartSeason(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        startedOn = C.VALID_STARTED_ON,
        endedOn = C.VALID_STARTED_ON,
        registrationEndedOn = C.VALID_REGISTRATION_ENDED_ON,
        qualifierRules = listOf(VALID_QUALIFIER_RULE),
        pointRules = listOf(VALID_POINT_RULE_INPUT)
    )

    val INVALID_START_SEASON: StartSeason = StartSeason(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        startedOn = C.INVALID_STARTED_ON,
        endedOn = C.INVALID_STARTED_ON,
        registrationEndedOn = C.INVALID_REGISTRATION_ENDED_ON,
        qualifierRules = listOf(INVALID_QUALIFIER_RULE),
        pointRules = listOf(INVALID_POINT_RULE_INPUT)
    )

    val VALID_LOWER_TIER: LowerTier = LowerTier(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        parentTierLevel = C.VALID_PARENT_TIER_LEVEL,
        tierName = C.VALID_TIER_NAME,
        startedOn = C.VALID_STARTED_ON,
        defaultTime = C.VALID_DEFAULT_TIME,
        defaultPoints = C.VALID_DEFAULT_POINTS,
        runnerLimit = C.VALID_RUNNER_LIMIT,
        registrationEndedOn = C.VALID_REGISTRATION_ENDED_ON,
        qualifierRules = listOf(VALID_QUALIFIER_RULE),
        pointRules = listOf(VALID_POINT_RULE_INPUT),
        divisionShifts = C.VALID_DIVISION_SHIFTS,
        promotionRules = listOf(VALID_QUALIFIER_RULE),
        relegationRules = listOf(VALID_QUALIFIER_RULE)
    )

    val INVALID_LOWER_TIER: LowerTier = LowerTier(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        parentTierLevel = C.INVALID_PARENT_TIER_LEVEL,
        tierName = C.INVALID_TIER_NAME,
        startedOn = C.INVALID_STARTED_ON,
        defaultTime = C.INVALID_DEFAULT_TIME,
        defaultPoints = C.INVALID_DEFAULT_POINTS,
        runnerLimit = C.INVALID_RUNNER_LIMIT,
        registrationEndedOn = C.INVALID_REGISTRATION_ENDED_ON,
        qualifierRules = listOf(INVALID_QUALIFIER_RULE),
        pointRules = listOf(INVALID_POINT_RULE_INPUT),
        divisionShifts = C.INVALID_DIVISION_SHIFTS,
        promotionRules = listOf(INVALID_QUALIFIER_RULE),
        relegationRules = listOf(INVALID_QUALIFIER_RULE)
    )
    
    val VALID_MATCH: Match = Match(
        race = C.VALID_RACE_NAME,
        homeRunner = C.VALID_RUNNER_NAME,
        awayRunner = C.VALID_RUNNER_NAME,
        startedOn = C.VALID_STARTED_ON
    )

    val INVALID_MATCH: Match = Match(
        race = C.INVALID_RACE_NAME,
        homeRunner = C.INVALID_RUNNER_NAME,
        awayRunner = C.INVALID_RUNNER_NAME,
        startedOn = C.INVALID_STARTED_ON
    )
    
    val VALID_LEAGUE_SCHEDULE: LeagueSchedule = LeagueSchedule(
        name = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL,
        matches = listOf(VALID_MATCH)
    )

    val INVALID_LEAGUE_SCHEDULE: LeagueSchedule = LeagueSchedule(
        name = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL,
        matches = listOf(INVALID_MATCH)
    )

    val VALID_LEAGUE_SEARCH: LeagueSearch = LeagueSearch(
        name = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL
    )

    val INVALID_LEAGUE_SEARCH: LeagueSearch = LeagueSearch(
        name = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL
    )
    
    val VALID_LEAGUE_DIVISION_SHIFT_UPDATE: LeagueDivisionShiftUpdate = LeagueDivisionShiftUpdate(
        leagueName = C.VALID_LEAGUE_NAME,
        season = C.VALID_SEASON,
        tierLevel = C.VALID_TIER_LEVEL,
        promotions = C.VALID_PROMOTIONS,
        relegations = C.VALID_RELEGATIONS
    )

    val INVALID_LEAGUE_DIVISION_SHIFT_UPDATE: LeagueDivisionShiftUpdate = LeagueDivisionShiftUpdate(
        leagueName = C.INVALID_LEAGUE_NAME,
        season = C.INVALID_SEASON,
        tierLevel = C.INVALID_TIER_LEVEL,
        promotions = C.INVALID_PROMOTIONS,
        relegations = C.INVALID_RELEGATIONS
    )

}
