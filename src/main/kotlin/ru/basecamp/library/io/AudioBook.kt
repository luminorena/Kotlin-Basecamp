package ru.basecamp.library.io

import ru.basecamp.library.model.Book

class AudioBook(
    title: String,
    author: String,
    year: Int,
    price: Double,
    initialCopies: Int = Int.MAX_VALUE,
    val durationMinutes: Int,
    val narrator: String,
    pages: Int = 0,
    isbn: String = ""
) : Book(title, author, year, pages, price, initialCopies, isbn) {

    override val category = "Аудиокнига"

    override fun printBookCard(
        isbn: String?,
        originalLanguage: String?,
        translator: String?,
        edition: String?,
        withFancyFrame: Boolean
    ) {
        super.printBookCard(isbn, originalLanguage, translator, edition, withFancyFrame)

        val hours = durationMinutes / 60
        val mins = durationMinutes % 60
        println("Длительность: ${hours}ч ${mins}мин, читает $narrator")
    }
}