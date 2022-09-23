/*
 * Copyright (c) 2022.
 */

package net.ngg.ddd.core

/**
 * Identifies a domain entity.
 */
@JvmInline
value class Id( val value: String) {
    companion object {
        fun generate() = Id(java.util.UUID.randomUUID().toString())
    }
}
/**
 * Common interface for all domain objets.
 */
interface DomainObject

/**
 * A domain Event is a message that is sent across the domain.
 * It's unique
 */
interface DomainEvent<T>: DomainObject

/**
 * A domain Entity is a persistent object that is identified by its unique id.
 * It's unique
 */
interface DomainEntity<T>: DomainObject {
    val id: Id
}

/**
 * Value Object is an immutable object that is identified by the equality of its properties.
 */
interface ValueObject<T>: DomainObject

/**
 * A domain Repository is a gateway to the domain.
 */
interface Repository<T: DomainEntity<T>> {

    fun save(entity: T)

    fun findById(id: Id): T?

    fun existsById(id: Id): Boolean

    fun findAll(): List<T>

    fun count(): Long

    fun delete(entity: T)

    fun deleteById(id: Id)

    fun deleteAll()
}

/**
 * Aggregate is a cluster of domain objects that can be treated as a single unit.
 */
interface Aggregate<T: DomainEntity<T>>: DomainObject {
    fun root(): T
}

/**
 * A domain Specification is a base interface that is satisfied by another domain object.
 */
typealias Specification<T> = (t: T) -> Boolean

/**
 * Checks if the given domain event is the same as this one.
 */
fun <K, T : DomainEvent<K>> T.sameEventAs(other: T): Boolean = this == other

/**
 * Checks if the given value object is the same as this one.
 */
fun <K, T: ValueObject<K>> T.sameValueAs(other: T): Boolean = this == other

/**
 * Checks if the given entity is the same as this one.
 */
fun <T: DomainEntity<T>> T.sameIdentityAs(other: T): Boolean = this.id == other.id

/**
 * A domain object can be satisfied by a specification.
 */
fun <T: DomainObject> T.satisfy(specification: Specification<T>): Boolean = specification(this)

/**
 * Chains two specifications together by ANDing them.
 */
fun <T> Specification<T>.and(other: Specification<T>): Specification<T> = { this(it) && other(it) }

/**
 * Chains two specifications together by ORing them.
 */
fun <T> Specification<T>.or(other: Specification<T>): Specification<T> = { this(it) || other(it) }

/**
 * Chains a negates a specification.
 */
fun <T> not(candidate: Specification<T>): Specification<T> = { !candidate(it) }

/**
 * Chains two specifications together by XORing them.
 */
fun <T> Specification<T>.xor(other: Specification<T>): Specification<T> = { this(it) xor other(it) }

/**
 * Chains two specifications together by NORing them.
 */
fun <T> Specification<T>.nor(other: Specification<T>): Specification<T> = { !this(it) && !other(it) }



