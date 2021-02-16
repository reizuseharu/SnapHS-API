package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.entity.Outcome
import com.reizu.snaphs.api.dto.output.Race as RaceOutput
import com.reizu.snaphs.api.entity.RaceRunner
import com.reizu.snaphs.api.service.seek.RaceRunnerSeekService
import com.reizu.snaphs.api.service.TestConstants as C
import com.reizu.snaphs.api.service.TestEntityConstants as EC
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class OpenRaceServiceTest {

    private val validRaceName: String = C.VALID_RACE_NAME
    private val invalidRaceName: String = C.INVALID_RACE_NAME

    private val validStartedOn: LocalDateTime = C.VALID_STARTED_ON
    private val invalidStartedOn: LocalDateTime = C.INVALID_STARTED_ON

    private val validRaceRunner: RaceRunner = EC.VALID_RACE_RUNNER

    private val validOpenRaces: List<RaceRunner> = listOf(validRaceRunner)
    private val validOpenRaceOutputs: List<RaceOutput> = validOpenRaces.map { openRace -> openRace.race.output }

    private val noOpenRaces: List<RaceRunner> = emptyList()
    private val noOpenRaceOutputs: List<RaceOutput> = emptyList()

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @InjectMocks
    private lateinit var openRaceService: OpenRaceService

    @BeforeAll
    fun setUp() {
        whenever(raceRunnerSeekService.findAllByStartedOnAndOutcome(validStartedOn, Outcome.PENDING_VERIFICATION)).thenReturn(validOpenRaces)
        whenever(raceRunnerSeekService.findAllByRaceAndOutcome(validRaceName, Outcome.PENDING_VERIFICATION)).thenReturn(noOpenRaces)

        whenever(raceRunnerSeekService.findAllByStartedOnAndOutcome(invalidStartedOn, Outcome.PENDING_VERIFICATION)).thenReturn(noOpenRaces)
        whenever(raceRunnerSeekService.findAllByRaceAndOutcome(invalidRaceName, Outcome.PENDING_VERIFICATION)).thenReturn(validOpenRaces)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid startedOn - when findOpenRaces - then return valid openRaceOutputs`() {
            val expectedOpenRaceOutputs: Iterable<RaceOutput> = validOpenRaceOutputs

            val actualOpenRaceOutputs: Iterable<RaceOutput> = openRaceService.findOpenRaces(validStartedOn)

            actualOpenRaceOutputs shouldEqual expectedOpenRaceOutputs
        }

        @Test
        fun `given valid race name - when isRaceOpen - then return true`() {
            val expectedRaceOpen = true

            val actualRaceOpen: Boolean = openRaceService.isRaceOpen(validRaceName)

            actualRaceOpen shouldEqual expectedRaceOpen
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid startedOn - when findOpenRaces - then return noOpenRaceOutputs`() {
            val expectedOpenRaceOutputs: Iterable<RaceOutput> = noOpenRaceOutputs

            val actualOpenRaceOutputs: Iterable<RaceOutput> = openRaceService.findOpenRaces(invalidStartedOn)

            actualOpenRaceOutputs shouldEqual expectedOpenRaceOutputs
        }

        @Test
        fun `given invalid race name - when isRaceOpen - then return false`() {
            val expectedRaceOpen = false

            val actualRaceOpen: Boolean = openRaceService.isRaceOpen(invalidRaceName)

            actualRaceOpen shouldEqual expectedRaceOpen
        }

    }

}
