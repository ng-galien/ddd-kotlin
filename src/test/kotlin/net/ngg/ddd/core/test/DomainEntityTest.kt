package net.ngg.ddd.core.test

import net.ngg.ddd.core.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

data class Person(val name: String, var age: Int) : DomainEntity<Person> {
    override val id: Id = Id(name)
}

data class BankAccount(val owner: Person, val balance: Int) : DomainEntity<BankAccount> {
    override val id: Id = Id.generate()
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

    @Test
    fun `should not be equal when id is not equal and one of them is transient`() {
        val person1 = Person("John", 30)
        val bankAccount = BankAccount(person1, 100)
        val bankAccount2 = BankAccount(person1, 100)
        Assertions.assertFalse(bankAccount.sameIdentityAs(bankAccount2))
    }
}