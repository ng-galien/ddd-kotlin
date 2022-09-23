package net.ngg.ddd.core.test

import net.ngg.ddd.core.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

data class Location(val x: Int, val y: Int): ValueObject<Location>

class ValueObjectTest {

    @Test
    fun `should be equal`() {
        val location1 = Location(1, 2)
        assertTrue(location1.sameValueAs(location1))
        val location2 = Location(1, 2)
        assertTrue(location1.sameValueAs(location2))
    }

    @Test
    fun `should not be equal`() {
        val location1 = Location(1, 2)
        val location2 = Location(2, 1)
        assertFalse(location1.sameValueAs(location2))
    }
}