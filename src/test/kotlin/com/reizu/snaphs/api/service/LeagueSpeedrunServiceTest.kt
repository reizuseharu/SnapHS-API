package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.*
import com.reizu.snaphs.api.exception.LeagueHasEndedException
import com.reizu.snaphs.api.dto.input.LeagueSpeedrun as LeagueSpeedrunInput
import com.reizu.snaphs.api.dto.output.LeagueSpeedrun as LeagueSpeedrunOutput
import com.reizu.snaphs.api.service.seek.LeagueSeekService
import com.reizu.snaphs.api.service.seek.LeagueSpeedrunSeekService
import com.reizu.snaphs.api.service.seek.SpeedrunSeekService
import com.nhaarman.mockitokotlin2.doNothing
import com.reizu.snaphs.api.service.TestConstants as C
import com.reizu.snaphs.api.service.TestEntityConstants as EC
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.IllegalArgumentException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LeagueSpeedrunServiceTest {

    private val validLeagueName: String = C.VALID_LEAGUE_NAME
    private val invalidLeagueName: String = C.INVALID_LEAGUE_NAME

    private val validLeagueType: LeagueType = C.VALID_LEAGUE_TYPE
    private val invalidLeagueType: LeagueType = C.INVALID_LEAGUE_TYPE

    private val validSeason: Int = C.VALID_SEASON
    private val invalidSeason: Int = C.INVALID_SEASON

    private val validTierLevel: Int = C.VALID_TIER_LEVEL
    private val invalidTierLevel: Int = C.INVALID_TIER_LEVEL

    private val validCategory: String = C.VALID_CATEGORY
    private val invalidCategory: String = C.INVALID_CATEGORY

    private val validGameName: String = C.VALID_GAME_NAME
    private val invalidGameName: String = C.INVALID_GAME_NAME

    private val validSystemName: String = C.VALID_SYSTEM_NAME
    private val invalidSystemName: String = C.INVALID_SYSTEM_NAME

    private val validIsEmulated: Boolean = C.VALID_IS_EMULATED
    private val invalidIsEmulated: Boolean = C.INVALID_IS_EMULATED

    private val validRegion: Region = C.VALID_REGION
    private val invalidRegion: Region = C.INVALID_REGION

    private val validVersion: String = C.VALID_VERSION
    private val invalidVersion: String = C.INVALID_VERSION

    private val validSearch: String = "leagueName:$validLeagueName"
    private val invalidSearch: String = "leagueName:$invalidLeagueName"

    private val validLeagueSpeedrunInput: LeagueSpeedrunInput = EC.VALID_LEAGUE_SPEEDRUN_INPUT
    private val invalidLeagueSpeedrunInput: LeagueSpeedrunInput = EC.INVALID_LEAGUE_SPEEDRUN_INPUT

    private val validLeagueSpeedrun: LeagueSpeedrun = EC.VALID_LEAGUE_SPEEDRUN
    private val invalidLeagueSpeedrun: LeagueSpeedrun = EC.INVALID_LEAGUE_SPEEDRUN

    private val validLeagueSpeedruns: List<LeagueSpeedrun> = listOf(validLeagueSpeedrun)
    private val validLeagueSpeedrunOutputs: List<LeagueSpeedrunOutput> = validLeagueSpeedruns.map { leagueSpeedrun -> leagueSpeedrun.output }

    private val noLeagueSpeedruns: List<LeagueSpeedrun> = emptyList()
    private val noLeagueSpeedrunOutputs: List<LeagueSpeedrunOutput> = emptyList()

    private val validLeague: League = EC.VALID_LEAGUE
    private val invalidLeague: League = EC.INVALID_LEAGUE

    private val validSpeedrun: Speedrun = EC.VALID_SPEEDRUN
    private val invalidSpeedrun: Speedrun = EC.INVALID_SPEEDRUN
    
    @Mock
    private lateinit var leagueSpeedrunSeekService: LeagueSpeedrunSeekService

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var speedrunSeekService: SpeedrunSeekService

    @Mock
    private lateinit var leagueService: LeagueService

    @InjectMocks
    private lateinit var leagueSpeedrunService: LeagueSpeedrunService

    @BeforeAll
    fun setUp() {
        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        whenever(speedrunSeekService.find(validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)).thenReturn(validSpeedrun)
        whenever(leagueSpeedrunSeekService.create(validLeagueSpeedrun)).thenReturn(validLeagueSpeedrun)
        whenever(leagueSpeedrunSeekService.findAllActive(search = validSearch)).thenReturn(validLeagueSpeedruns)
        whenever(leagueSpeedrunSeekService
            .findAll(validLeagueName, validLeagueType, validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)
        ).thenReturn(validLeagueSpeedruns)

        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        whenever(speedrunSeekService.find(invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)).thenReturn(invalidSpeedrun)
        doThrow(IllegalArgumentException::class).whenever(leagueSpeedrunSeekService).create(invalidLeagueSpeedrun)
        whenever(leagueSpeedrunSeekService.findAllActive(search = invalidSearch)).thenReturn(noLeagueSpeedruns)
        whenever(leagueSpeedrunSeekService.findAll(invalidLeagueName, invalidLeagueType, invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)).thenReturn(noLeagueSpeedruns)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid leagueSpeedrunInput - when create - then return valid leagueSpeedrunOutput`() {
            val expectedLeagueSpeedrunOutput: LeagueSpeedrunOutput = validLeagueSpeedrun.output

            val actualLeagueSpeedrunOutput: LeagueSpeedrunOutput = leagueSpeedrunService.create(validLeagueSpeedrunInput)

            actualLeagueSpeedrunOutput shouldEqual expectedLeagueSpeedrunOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid leagueSpeedrunOutputs`() {
            val expectedLeagueSpeedrunOutputs: Iterable<LeagueSpeedrunOutput> = validLeagueSpeedrunOutputs

            val actualLeagueSpeedrunOutputs: Iterable<LeagueSpeedrunOutput> = leagueSpeedrunService.findAll(validSearch)

            actualLeagueSpeedrunOutputs shouldEqual expectedLeagueSpeedrunOutputs
        }

        @Test
        fun `given valid parameters - when findAll - then return valid leagueSpeedrunOutputs`() {
            val expectedLeagueSpeedrunOutputs: Iterable<LeagueSpeedrunOutput> = validLeagueSpeedrunOutputs

            val actualLeagueSpeedrunOutputs: Iterable<LeagueSpeedrunOutput> = leagueSpeedrunService.findAll(validLeagueName, validLeagueType, validCategory, validGameName, validSystemName, validIsEmulated, validRegion, validVersion)

            actualLeagueSpeedrunOutputs shouldEqual expectedLeagueSpeedrunOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid leagueSpeedrunInput - when create - then throw IllegalArgumentException`() {
            doNothing().whenever(leagueService).validateLeagueChange(invalidLeague.endedOn)

            invoking { leagueSpeedrunService.create(invalidLeagueSpeedrunInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given completed league - when create - then throw LeagueHasEndedException`() {
            doThrow(LeagueHasEndedException::class).whenever(leagueService).validateLeagueChange(invalidLeague.endedOn)

            invoking { leagueSpeedrunService.create(invalidLeagueSpeedrunInput) } shouldThrow LeagueHasEndedException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no leagueSpeedrunOutputs`() {
            val expectedLeagueSpeedrunOutputs: Iterable<LeagueSpeedrunOutput> = noLeagueSpeedrunOutputs

            val actualLeagueSpeedrunOutputs: Iterable<LeagueSpeedrunOutput> = leagueSpeedrunService.findAll(invalidSearch)

            actualLeagueSpeedrunOutputs shouldEqual expectedLeagueSpeedrunOutputs
        }

        @Test
        fun `given invalid parameters - when findAll - then return no leagueSpeedrunOutputs`() {
            val expectedLeagueSpeedrunOutputs: Iterable<LeagueSpeedrunOutput> = noLeagueSpeedrunOutputs

            val actualLeagueSpeedrunOutputs: Iterable<LeagueSpeedrunOutput> = leagueSpeedrunService.findAll(invalidLeagueName, invalidLeagueType, invalidCategory, invalidGameName, invalidSystemName, invalidIsEmulated, invalidRegion, invalidVersion)

            actualLeagueSpeedrunOutputs shouldEqual expectedLeagueSpeedrunOutputs
        }

    }
}
