package com.reizu.snaphs.api.controller

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LeagueControllerTest {

    @BeforeAll
    fun setUp() {}

    @Test
    fun create() {}

    @Test
    fun findAll() {}

    @Test
    fun endSeason() {}

    @Test
    fun startNewSeason() {}

    @Test
    fun addLowerTier() {}

    @Test
    fun modifyDivisionShifts() {}

    @Test
    fun generateRoundRobin() {}
}
