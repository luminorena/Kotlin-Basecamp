package ru.basecamp.library.model

interface Searchable {
    fun matches(query: String): Boolean = false
}