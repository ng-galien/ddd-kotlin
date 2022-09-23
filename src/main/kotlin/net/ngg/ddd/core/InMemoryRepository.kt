package net.ngg.ddd.core

/**
 * A marker interface for all domain events.
 */
class InMemoryRepository<ID, T: DomainEntity<ID, T>>: Repository<ID, T> {

    private val entities = mutableMapOf<ID, T>()

    override fun save(entity: T) {
        entities[entity.id()] = entity
    }

    override fun findById(id: ID): T? {
        return entities[id]
    }

    override fun existsById(id: ID): Boolean {
        return entities.containsKey(id)
    }

    override fun findAll(): List<T> {
        return entities.values.toList()
    }

    override fun count(): Long {
        return entities.size.toLong()
    }

    override fun deleteAll() {
        entities.clear()
    }

    override fun deleteById(id: ID) {
        entities.remove(id)
    }

    override fun delete(entity: T) {
        entities.remove(entity.id())
    }

}