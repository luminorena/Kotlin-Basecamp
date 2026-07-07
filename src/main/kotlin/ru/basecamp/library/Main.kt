package ru.basecamp.library

import ru.basecamp.library.error.BookAlreadyExistsException
import ru.basecamp.library.error.BookNotFoundException
import ru.basecamp.library.error.CatalogCorruptedException
import ru.basecamp.library.error.InvalidIsbnException
import ru.basecamp.library.error.NotAvailableException
import ru.basecamp.library.helpers.parseYearResult
import ru.basecamp.library.helpers.safelyParseYear
import ru.basecamp.library.io.Library
import ru.basecamp.library.model.SimpleBook

fun main() {
    val lib = Library()
    val cleanCode = SimpleBook(
        title = "Чистый код",
        author = "Р. Мартин",
        year = 2008,
        pages = 464,
        price = 1290.0,
        initialCopies = 0,
        isbn = "9785916719892"
    )

    val book = SimpleBook(
        title = "Test",
        author = "T. Test",
        year = 2008,
        pages = 464,
        price = 190.0,
        initialCopies = 0,
        isbn = "7685916719882"
    )

    val books = listOf(cleanCode, book)

    cleanCode.printBookCard(
        "9785916719892",
        "English",
        "Translator",
        "Test",
        false
    )

    lib.addBook(cleanCode)

    try {
        lib.addBook(cleanCode)
    } catch (e: BookAlreadyExistsException) {
        println("Книга уже существует: ${e.message}")
    }

    try {
        lib.getByIsbn("0000000000000")
    } catch (e: BookNotFoundException) {
        println("Книга не найдена: ${e.message}, ISBN был: ${e.isbn}")
    }

    try {
        lib.lendOrThrow(cleanCode)
    } catch (e: NotAvailableException) {
        println("Книга не доступна: ${e.message}")
    }

    try {
        lib.parseIsbn("0000000000000")
    } catch (e: InvalidIsbnException) {
        println("ISBN не валиден: ${e.message}")
    }

    try {
        lib.parseIsbnExplicit("0000000000000")
    } catch (e: InvalidIsbnException) {
        println("ISBN не валиден: ${e.message}")
    }

    try {
        val exportToString = lib.exportToString("Test", books)
        println(exportToString)

    } catch (e: CatalogCorruptedException) {
        println("Невозможно перевести в строковый формат: ${e.message}")
    }


    val year = safelyParseYear("2008")
    val bad = safelyParseYear("xyz")

    println(year)
    println(bad)

    parseYearResult("2008").fold(
        onSuccess = { println("Год: $it") },
        onFailure = { println("Ошибка: ${it.message}") }
    )
}


