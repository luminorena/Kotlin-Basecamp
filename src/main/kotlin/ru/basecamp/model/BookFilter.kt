package ru.basecamp.model

fun interface BookFilter {
    operator fun invoke(book: Book): Boolean
}