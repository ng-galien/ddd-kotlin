package net.ngg.ddd.core.test

import net.ngg.ddd.core.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

data class SaleEvent(val id: String, val name: String, val price: Double, val quantity: Int)
    : DomainEvent<SaleEvent>

class DomainEventTest {

    @Test
    fun `should be equal`() {
        val saleEvent1 = SaleEvent("1", "name", 10.0, 1)
        val saleEvent2 = SaleEvent("1", "name", 10.0, 1)
        Assertions.assertTrue(saleEvent1.sameEventAs(saleEvent2))
    }

    @Test
    fun `should not be equal`() {
        val saleEvent1 = SaleEvent("1", "name", 10.0, 1)
        val saleEvent2 = SaleEvent("2", "name", 10.0, 1)
        Assertions.assertFalse(saleEvent1.sameEventAs(saleEvent2))
    }
}