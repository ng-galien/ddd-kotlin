package net.ngg.ddd.core.test

import net.ngg.ddd.core.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

data class Product(val id: Int, val name: String, val price: Double):
    DomainEntity<Product, Int> {
    override fun id() = id
}

val expensive: Specification<Product> = { it.price > 100.0 }
val cheap: Specification<Product> = { it.price < 100.0 }
val anIPhone: Specification<Product> = { it.name == "iPhone" }
val anAndroid: Specification<Product> = { it.name == "Android" }
val aBlackBerry: Specification<Product> = { it.name == "BlackBerry" }

class SpecificationTest {

    @Test
    fun `should be able to use specification`() {
        val product = Product(1, "iPhone", 1000.0)
        assertTrue(product.satisfy(anIPhone))
        assertTrue(product.satisfy(expensive))
        assertFalse(product.satisfy(cheap))
    }

    @Test
    fun `should be able to use specification with not`() {
        val product = Product(1, "iPhone", 1000.0)
        assertTrue(product.satisfy(not(anAndroid)))
        assertFalse(product.satisfy(not(anIPhone)))
    }

    @Test
    fun `should be able to use specification with and`() {
        val product = Product(1, "iPhone", 1000.0)
        assertTrue(product.satisfy(anIPhone.and(expensive)))
        assertFalse(product.satisfy(anIPhone.and(cheap)))
        assertFalse(product.satisfy(cheap.and(anIPhone)))
        assertFalse(product.satisfy(cheap.and(anAndroid)))
    }

    @Test
    fun `should be able to use specification with or`() {
        val product = Product(1, "iPhone", 1000.0)
        assertTrue(product.satisfy(anIPhone.or(expensive)))
        assertTrue(product.satisfy(anAndroid.or(anIPhone)))
        assertTrue(product.satisfy(anAndroid.or(anIPhone)))
        assertFalse(product.satisfy(cheap.or(anAndroid)))
    }

    @Test
    fun `should be able to use specification with xor`() {
        val product = Product(1, "iPhone", 1000.0)
        assertTrue(product.satisfy(anIPhone.xor(anAndroid)))
        assertFalse(product.satisfy(anIPhone.xor(expensive)))
    }

    @Test
    fun `should be able to use specification with and nor`() {
        val product = Product(1, "BlackBerry", 100.0)
        assertTrue(product.satisfy(anIPhone.nor(anAndroid)))
        assertTrue(product.satisfy(anIPhone.nor(anAndroid)))
        assertFalse(product.satisfy(aBlackBerry.nor(cheap)))
    }

    @Test
    fun `should be able to use multiple specification`() {
        val mySpec = not(aBlackBerry).and(cheap)
        val product = Product(1, "Android", 99.0)
        assertTrue(product.satisfy(mySpec))
    }
}