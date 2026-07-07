package ru.basecamp.library.io

import ru.basecamp.library.error.BookAlreadyExistsException
import ru.basecamp.library.error.BookNotFoundException
import ru.basecamp.library.error.InvalidIsbnException
import ru.basecamp.library.error.NotAvailableException
import ru.basecamp.library.model.Book
import ru.basecamp.library.model.LoanResult
import ru.basecamp.library.model.SimpleBook
import java.io.StringWriter

class Library {

    private val CLEAN_REGEX = Regex("[\\s-]")
    private val DIGITS_REGEX = Regex("\\d+")

    private val books = mutableListOf(
        SimpleBook(
            title = "Kotlin in Action",
            author = "Dmitry Jemerov",
            year = 2017,
            pages = 360,
            price = 45.0,
            initialCopies = 3,
            isbn = "978-1"
        ),
        SimpleBook(
            title = "Effective Java",
            author = "Joshua Bloch",
            year = 2018,
            pages = 416,
            price = 55.0,
            initialCopies = 2,
            isbn = "978-2"
        )
    )

    fun getByIsbn(isbn: String): Book =
        byIsbn(isbn)

    fun addBook(book: SimpleBook) {
        val isbn = book.isbn ?: return
        byIsbn(isbn)
        throw BookAlreadyExistsException(isbn)
    }

    fun lendOrThrow(book: Book) {
        val result = book.lend()
        when (result) {
            is LoanResult.Success -> {}
            is LoanResult.NotAvailable -> throw NotAvailableException(book.title)
            else -> error("Unexpected result: $result")
        }
    }

    fun byIsbn(isbn: String?): SimpleBook {
        val normalizedIsbn = isbn?.trim()
            ?: throw IllegalArgumentException("isbn is null")

        if (normalizedIsbn.isEmpty()) {
            throw IllegalArgumentException("isbn is blank")
        }

        return books.firstOrNull { it.isbn == normalizedIsbn }
            ?: throw BookNotFoundException(normalizedIsbn)
    }

    fun isValidChecksum(rawIsbn: String?): Boolean {
        val cleanIsbn = rawIsbn?.replace(CLEAN_REGEX, "")

        if (cleanIsbn?.length != 13 || !cleanIsbn.matches(DIGITS_REGEX)) {
            return false
        }

        var result = 0
        for (i in cleanIsbn.indices) {
            result += if (i % 2 == 0) {
                cleanIsbn[i].digitToInt() * 1
            } else {
                cleanIsbn[i].digitToInt() * 3
            }
        }

        if (result % 10 == 0) {
            println("ISBN валиден, контрольная сумма равна $result")
            return true
        } else {
            println("ISBN не валиден, контрольная сумма равна $result, попробуйте ещё раз")
            return false
        }
    }

    fun parseIsbn(raw: String): String {
        val cleaned = raw.replace("-", "").replace(" ", "")
        require(cleaned.length == 13) {
            throw InvalidIsbnException(raw, "ожидаемая длина 13, получено ${cleaned.length}")
        }
        require(cleaned.all { it.isDigit() }) {
            throw InvalidIsbnException(raw, "содержит нецифровые символы")
        }
        require(isValidChecksum(cleaned)) {
            throw InvalidIsbnException(raw, "контрольная сумма не сходится")
        }
        return cleaned
    }

    fun parseIsbnExplicit(raw: String): String {
        val cleaned = raw.replace("-", "").replace(" ", "")
        if (cleaned.length != 13) throw InvalidIsbnException(raw, "длина ${cleaned.length}, нужна 13")
        if (!cleaned.all { it.isDigit() }) throw InvalidIsbnException(raw, "не только цифры")
        if (!isValidChecksum(cleaned)) throw InvalidIsbnException(raw, "плохая контрольная сумма")
        return cleaned
    }

    fun exportToString(title: String, books: List<Book>): String = StringWriter().use { writer ->
        writer.write("# Каталог: $title\n")
        for (book in books) {
            writer.write("${book.isbn ?: "no-isbn"}\t${book.title}\t${book.author}\t${book.year}\n")
        }
        writer.toString()
    }
}