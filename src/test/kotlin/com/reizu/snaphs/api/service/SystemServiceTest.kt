package com.reizu.snaphs.api.service

import com.reizu.snaphs.api.dto.input.System as SystemInput
import com.reizu.snaphs.api.dto.output.System as SystemOutput
import com.reizu.snaphs.api.entity.System
import com.reizu.snaphs.api.service.seek.SystemSeekService
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

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SystemServiceTest {

    private val validName: String = C.VALID_SYSTEM_NAME
    private val invalidName: String = C.INVALID_SYSTEM_NAME

    private val validSearch: String? = "name:$validName"
    private val invalidSearch: String? = "name:$invalidName"

    private val validSystemInput: SystemInput = EC.VALID_SYSTEM_INPUT
    private val invalidSystemInput: SystemInput = EC.INVALID_SYSTEM_INPUT

    private val validSystem: System = EC.VALID_SYSTEM
    private val invalidSystem: System = EC.INVALID_SYSTEM

    private val validSystems: List<System> = listOf(validSystem)
    private val validSystemOutputs: List<SystemOutput> = validSystems.map { system -> system.output }

    private val noSystems: List<System> = emptyList()
    private val noSystemOutputs: List<SystemOutput> = emptyList()

    @Mock
    private lateinit var systemSeekService: SystemSeekService

    @InjectMocks
    private lateinit var systemService: SystemService

    @BeforeAll
    fun setUp() {
        whenever(systemSeekService.findAllActive(search = validSearch)).thenReturn(validSystems)
        whenever(systemSeekService.create(validSystem)).thenReturn(validSystem)

        whenever(systemSeekService.findAllActive(search = invalidSearch)).thenReturn(noSystems)
        doThrow(IllegalArgumentException::class).whenever(systemSeekService).create(invalidSystem)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid systemInput - when create - then return valid systemOutput`() {
            val expectedSystemOutput: SystemOutput = validSystem.output

            val actualSystemOutput: SystemOutput = systemService.create(validSystemInput)

            actualSystemOutput shouldEqual expectedSystemOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid systemOutputs`() {
            val expectedSystemOutputs: Iterable<SystemOutput> = validSystemOutputs

            val actualSystemOutputs: Iterable<SystemOutput> = systemService.findAll(validSearch)

            actualSystemOutputs shouldEqual expectedSystemOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid systemInput - when create - then throw IllegalArgumentException`() {
            invoking { systemService.create(invalidSystemInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no systemOutputs`() {
            val expectedSystemOutputs: Iterable<SystemOutput> = noSystemOutputs

            val actualSystemOutputs: Iterable<SystemOutput> = systemService.findAll(invalidSearch)

            actualSystemOutputs shouldEqual expectedSystemOutputs
        }

    }

}
