package net.ngg.ddd.core

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
interface DomainEntity<T,ID>: DomainObject {
    fun id(): ID
}

/**
 * Value Object is an immutable object that is identified by the equality of its properties.
 */
interface ValueObject<T>: DomainObject

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
fun <K, ID, T: DomainEntity<K, ID>> T.sameIdentityAs(other: T): Boolean = this.id() == other.id()

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
 * Negates a specification.
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



