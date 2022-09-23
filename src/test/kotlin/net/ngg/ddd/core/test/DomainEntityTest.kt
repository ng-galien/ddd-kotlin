package net.ngg.ddd.core.test

import net.ngg.ddd.core.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

data class Person(val name: String, var age: Int) : DomainEntity<String, Person> {
    override fun id(): String = name
}


class DomainEntityTest {

    @Test
    fun `should be equal when id is equal`() {
        val person1 = Person("John", 30)
        val person2 = Person("John", 40)
        Assertions.assertTrue(person1.sameIdentityAs(person2))
        person1.age = 40
        Assertions.assertTrue(person1.sameIdentityAs(person2))
    }

    @Test
    fun `should not be equal when id is not equal`() {
        val person1 = Person("John", 30)
        val person2 = Person("Jane", 30)
        Assertions.assertFalse(person1.sameIdentityAs(person2))
    }
}