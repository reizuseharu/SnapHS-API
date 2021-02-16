package com.reizu.snaphs.api.service.seek

import com.reizu.snaphs.api.entity.System
import com.reizu.snaphs.api.repository.SystemRepository
import com.reizu.snaphs.api.service.TestConstants as C
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
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SystemSeekServiceTest {

    private val validName: String = C.VALID_SYSTEM_NAME
    private val invalidName: String = C.INVALID_SYSTEM_NAME

    @Mock
    private lateinit var validSystem: System

    @Mock
    private lateinit var systemRepository: SystemRepository

    @InjectMocks
    private lateinit var systemSeekService: SystemSeekService

    @BeforeAll
    fun setUp() {
        whenever(systemRepository.findByNameAndRemovedOnIsNull(validName)).thenReturn(validSystem)

        whenever(systemRepository.findByNameAndRemovedOnIsNull(invalidName)).thenReturn(null)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid name - when findByName - then return valid system`() {
            val expectedSystem: System = validSystem

            val actualSystem = systemSeekService.findByName(validName)

            actualSystem shouldEqual expectedSystem
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid name - when findByName - then throw EntityNotFoundException`() {
            invoking { systemSeekService.findByName(invalidName) } shouldThrow EntityNotFoundException::class
        }

    }

}
