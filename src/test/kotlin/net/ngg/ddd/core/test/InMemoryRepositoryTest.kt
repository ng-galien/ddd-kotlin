package net.ngg.ddd.core.test

import net.ngg.ddd.core.DomainEntity
import net.ngg.ddd.core.InMemoryRepository
import net.ngg.ddd.core.sameIdentityAs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

data class Customer(val id: String, val name: String): DomainEntity<String, Customer> {
    override fun id(): String = id

}

class InMemoryRepositoryTest {

    @Test
    fun `should save and retrieve customer`() {
        val repository = InMemoryRepository<String, Customer>()
        val customer = Customer("1", "John Doe")
        repository.save(customer)
        val retrievedCustomer = repository.findById("1")
        Assertions.assertTrue(retrievedCustomer?.sameIdentityAs(customer) ?: false)
    }

    @Test
    fun `should return null when customer not found`() {
        val repository = InMemoryRepository<String, Customer>()
        val retrievedCustomer = repository.findById("1")
        Assertions.assertNull(retrievedCustomer)
    }

    @Test
    fun `should return all customers`() {
        val repository = InMemoryRepository<String, Customer>()
        val customer1 = Customer("1", "John Doe")
        val customer2 = Customer("2", "Jane Doe")
        repository.save(customer1)
        repository.save(customer2)
        val retrievedCustomers = repository.findAll()
        Assertions.assertEquals(2, retrievedCustomers.size)
        Assertions.assertTrue(retrievedCustomers.any { it.sameIdentityAs(customer1) })
        Assertions.assertTrue(retrievedCustomers.any { it.sameIdentityAs(customer2) })
    }

    @Test
    fun `should delete customer`() {
        val repository = InMemoryRepository<String, Customer>()
        val customer = Customer("1", "John Doe")
        repository.save(customer)
        repository.delete(customer)
        val retrievedCustomer = repository.findById("1")
        Assertions.assertNull(retrievedCustomer)
    }

    @Test
    fun `should delete customer by id`() {
        val repository = InMemoryRepository<String, Customer>()
        val customer = Customer("1", "John Doe")
        repository.save(customer)
        repository.deleteById("1")
        val retrievedCustomer = repository.findById("1")
        Assertions.assertNull(retrievedCustomer)
    }

    @Test
    fun `should delete all customers`() {
        val repository = InMemoryRepository<String, Customer>()
        val customer1 = Customer("1", "John Doe")
        val customer2 = Customer("2", "Jane Doe")
        repository.save(customer1)
        repository.save(customer2)
        repository.deleteAll()
        val retrievedCustomers = repository.findAll()
        Assertions.assertEquals(0, retrievedCustomers.size)
    }

    @Test
    fun `should return true when customer exists`() {
        val repository = InMemoryRepository<String, Customer>()
        val customer = Customer("1", "John Doe")
        repository.save(customer)
        Assertions.assertTrue(repository.existsById("1"))
    }

    @Test
    fun `should return the count of customers`() {
        val repository = InMemoryRepository<String, Customer>()
        val customer1 = Customer("1", "John Doe")
        val customer2 = Customer("2", "Jane Doe")
        repository.save(customer1)
        repository.save(customer2)
        Assertions.assertEquals(2, repository.count())
    }

    @Test
    fun `should return false when customer does not exist`() {
        val repository = InMemoryRepository<String, Customer>()
        Assertions.assertFalse(repository.existsById("1"))
    }

}