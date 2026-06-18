package ru.basecamp.library.model

import ru.basecamp.library.io.BookInput
import ru.basecamp.library.io.BookPrinter
import ru.basecamp.library.util.Validation

/**
 * Перенести все методы по пакетам (позиционные параметры) и сделать main()
 */

class Book(
    val title: String,
    val author: String,
    val year: Int,
    val pages: Int,
    val price: Double,
    val initialCopies: Int,
    val isbn: String? = null,
    val edition: String? = null,
    val originalLanguage: String? = null,
    val translator: String? = null
) {
    var copiesInStock: Int = initialCopies
        private set
    var totalLoans: Int = 0
        private set
    var totalPages: Int = 0
        private set

    init {
        require(title.isNotBlank()) { "Название не может быть пустым" }
        require(author.isNotBlank()) { "Автор не может быть пустым" }
        require(year in 1450..2100) { "Год $year вне допустимого диапазона" }
        require(pages > 0) { "Страниц должно быть положительно, а не $pages" }
        require(price >= 0) { "Цена не может быть отрицательной" }
        require(initialCopies >= 0) { "Количество экземпляров не может быть отрицательным" }
    }

    constructor(title: String, author: String, year: Int, pages: Int, price: Double, copies: Int) :
            this(title, author, year, pages, price, copies,
                isbn = null, edition = null, originalLanguage = null, translator = null)
}


fun main() {
    val readBook = BookInput()
    readBook.readBookData()

    val printBook = BookPrinter(author = "Лев Толстой",
        title = "Война и мир",
        year = 1869,
        pages = 1228,
        price = 1500.0,
        initialCopies = 50)

    printBook.printBookCard(withFancyFrame = true)

    repeat(5) { i ->
        val ok = printBook.lend()
        println("Выдача ${i + 1}: ${if (ok) "ок, осталось ${printBook.copiesInStock}" else "отказ"}")
    }
    printBook.returnCopy()
    println("После возврата: ${printBook.copiesInStock}, всего выдач: ${printBook.totalLoans}")

    val validatedBook = Validation(author = "Лев Толстой",
        title = "Война и мир",
        year = 1869,
        pages = 1228,
        price = 1500.0,
        initialCopies = 50)

    validatedBook.isAvailable
    validatedBook.shortTitle
    validatedBook.cleanIsbn("1234567853333")
    validatedBook.validateBook()
}