package ru.basecamp.library.io

import ru.basecamp.library.model.SimpleBook

class EBook(
    title: String,
    author: String,
    year: Int,
    val sizeMb: Double,
    val format: String,
    pages: Int = 0,
    price: Double,
    isbn: String = ""
) : SimpleBook(title, author, year, pages, price, initialCopies = Int.MAX_VALUE, isbn) {

    override val category = "Электронная книга"

    fun lend(): Boolean {
        totalLoans++
        return true
    }

    override fun printBookCard(
        isbn: String?,
        originalLanguage: String?,
        translator: String?,
        edition: String?,
        withFancyFrame: Boolean
    ) {
        super.printBookCard(isbn, originalLanguage, translator, edition, withFancyFrame)
        println("Формат: $format, $sizeMb MB")
    }
}