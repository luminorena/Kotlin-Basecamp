package ru.basecamp.io

import ru.basecamp.model.Book
import ru.basecamp.model.Genre

open class Library(private val books: MutableList<Book> = mutableListOf()) {

    fun add(book: Book) = books.add(book)

    open fun search(predicate: (Book) -> Boolean): List<Book> = books.filter(predicate)

    fun all(): List<Book> = books.toList()

    open fun findByIsbn(isbn: String): Book? =
        books.find { it.isbn == isbn }

    fun forEachBook(action: (Book) -> Unit) {
        books.forEach(action)
    }

    fun <R> mapBooks(transform: (Book) -> R): List<R> = books.map(transform)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val lib = Library()

            lib.add(Book("Crime and Punishment", "Fyodor Dostoevsky", 1866, 671, 9.99, 3))
            lib.add(Book("The Idiot", "Fyodor Dostoevsky", 1869, 656, 8.99, 2))
            lib.add(Book("War and Peace", "Leo Tolstoy", 1869, 1225, 14.99, 1))

            val classics = lib.search { it.year < 1950 }
            val cheap = lib.search { it.price < 500 }
            val byAuthor = lib.search { it.author == "Л. Толстой" }
            val comboSearch = lib.search { it.genre == Genre.FICTION && it.year > 2000 }

            val titles = lib.all().map { it.title }
            val printed = lib.all().filterIsInstance<PrintedBook>()
            val isPositive: (Int) -> Boolean = { it > 0 }

            val titlesTransformed = lib.all().map(Book::title)
            val isAvailable = lib.all().filter(Book::isAvailable)
            val authorTransformed = lib.all().map(Book::author)

            val newCode = Book(
                title = "Clean Code",
                author = "Robert C. Martin",
                year = 2008,
                pages = 464,
                price = 30.0,
                initialCopies = 2,
                genre = Genre.NON_FICTION
            )

            val doLend = newCode::lend
            val result = doLend()

            lib.forEachBook { println("- ${it.title} (${it.year})") }
            val totalPages = lib.mapBooks { (it as? PrintedBook)?.pages ?: 0 }.sum()

            fun yearAfter(year: Int): (Book) -> Boolean = { it.year > year }
            fun authorFilter(author: String): (Book) -> Boolean = { it.author == author }
            fun genreFilter(genre: Genre): (Book) -> Boolean = { it.genre == genre }

            infix fun ((Book) -> Boolean).and(other: (Book) -> Boolean): (Book) -> Boolean = {
                this(it) && other(it)
            }

            val cheapModernBook = lib.search(yearAfter(2000) and { it.price > 1000 })
        }
    }
}