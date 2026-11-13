package ru.basecamp.library.dsl

import ru.basecamp.model.Book
import ru.basecamp.model.Genre
import ru.basecamp.model.Library
import ru.basecamp.model.Money
import ru.basecamp.model.PrintedBook

@DslMarker
annotation class LibraryDsl

@LibraryDsl
class BookBuilder {
    var title: String = ""
    var author: String = ""
    var year: Int = 0
    var pages: Int = 100
    var price: Double = 0.0
    var copies: Int = 1
    var isbn: String? = null
    var genre: Genre = Genre.OTHER

    fun build(): PrintedBook {
        require(title.isNotBlank()) { "Book must have title" }
        require(author.isNotBlank()) { "Book must have author" }

        val checkedIsbn = requireNotNull(isbn) { "Book must have ISBN" }

        return PrintedBook(title = title, author = author, year = year, pages = pages, genre = genre,
            price = Money(price, "RUB"),
            initialCopies = copies,
            isbn = checkedIsbn
        )
    }
}

@LibraryDsl
class LibraryBuilder(private val name: String) {
    private val books = mutableListOf<Book>()

    fun book(configure: BookBuilder.() -> Unit) {
        val builder = BookBuilder()
        builder.configure()
        books.add(builder.build())

    }

    fun build(): Library = Library(name).apply {
        books.forEach { addBook(it as Book) }
    }
}

fun library(name: String, configure: LibraryBuilder.() -> Unit): Library {
    val builder = LibraryBuilder(name)
    builder.configure()
    return builder.build()
}