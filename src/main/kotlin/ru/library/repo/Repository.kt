package ru.library.repo

import java.util.ArrayList
import java.util.HashMap
import java.util.NoSuchElementException

interface Identifiable<ID> {
    val id: ID
}

class Repository<ID, T : Identifiable<ID>> {

    private val items: MutableMap<ID, T> = HashMap()

    val size: Int
        get() = items.size

    fun add(item: T) {
        if (items.containsKey(item.id)) {
            throw IllegalArgumentException("Duplicate ID: ${item.id}")
        }
        items.put(item.id, item)
    }

    fun get(id: ID): T? {
        return items.get(id)
    }

    fun getOrThrow(id: ID): T {
        val item = items.get(id)
        if (item == null) {
            throw NoSuchElementException("No item with id $id")
        }
        return item
    }

    fun remove(id: ID): T? {
        return items.remove(id)
    }

    fun all(): List<T> {
        return ArrayList(items.values)
    }

    fun findAll(predicate: (T) -> Boolean): List<T> {
        val result = ArrayList<T>()
        for (item in items.values) {
            if (predicate(item)) {
                result.add(item)
            }
        }
        return result
    }
}