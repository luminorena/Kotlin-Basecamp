package ru.basecamp.library.io

import ru.basecamp.library.model.Book

class EBook(
    title: String,
    author: String,
    year: Int,
    val sizeMb: Double,
    val format: String,
    pages: Int = 0,
    price: Double,
    isbn: String = ""
) : Book(title, author, year, pages, price, initialCopies = Int.MAX_VALUE, isbn) {

    override val category = "Электронная книга"

    override fun lend(): Boolean {
        totalLoans++
        return true
    }

    override fun returnCopy() {
        println("Электронная книга не имеет физических копий")
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