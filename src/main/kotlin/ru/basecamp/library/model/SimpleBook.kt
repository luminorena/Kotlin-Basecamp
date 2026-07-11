package ru.basecamp.library.model

open class SimpleBook(
    title: String,
    author: String,
    year: Int,
    pages: Int,
    price: Double,
    initialCopies: Int,
    isbn: String? = null,
    edition: String? = null,
    originalLanguage: String? = null,
    translator: String? = null
) : Book(
    title = title,
    author = author,
    year = year,
    pages = pages,
    price = price,
    initialCopies = initialCopies,
    isbn = isbn,
    edition = edition,
    originalLanguage = originalLanguage,
    translator = translator
) {
    override val category: String = "General"
}