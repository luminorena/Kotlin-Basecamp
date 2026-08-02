package ru.basecamp.model

object LibraryRegistry {
    private val books = mutableListOf<Book>()
    fun register(book: Book) {
        books.add(book)
        println("Добавлено в каталог: ${book.title}")
    }
    fun count(): Int = books.size
    fun summary(): String =
        "Каталог: ${books.size} книг, всего выдач ${books.sumOf { it.totalLoans }}"

    fun topTwo(): Pair<Book?, Book?> {
        val sorted = books.sortedByDescending { it.totalLoans }
        return sorted.getOrNull(0) to sorted.getOrNull(1)
    }
}