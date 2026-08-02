package ru.basecamp

import ru.basecamp.io.LoanResult
import ru.basecamp.model.Genre
import ru.basecamp.model.LibraryRegistry
import ru.basecamp.model.Money
import ru.basecamp.model.PrintedBook

fun main() {

    val book1 = PrintedBook(
        title = "Test",
        author = "T. Test",
        year = 2025,
        pages = 464,
        price = Money(190.5, "RUB"),
        initialCopies = 0,
        isbn = "7685916719882",
        genre = Genre.PROGRAMMING
    )

    val book2 = PrintedBook(
        title = "Test",
        author = "T. Test",
        year = 2008,
        pages = 464,
        price = Money(300.5, "RUB"),
        initialCopies = 0,
        isbn = "7685916719993",
        genre = Genre.SCIENCE
    )
    when (val result = book1.lend()) {
        is LoanResult.Success -> println("✓ Выдано")
        is LoanResult.NotAvailable -> println("✗ Нет в наличии (доступно: ${result.available})")
        is LoanResult.TooManyOnHand -> println("✗ Превышен лимит ${result.limit}")
        is LoanResult.BookNotInLibrary -> println("✗ Книги нет в каталоге")
    }

    LibraryRegistry.register(book1)
    LibraryRegistry.register(book2)
    println(LibraryRegistry.summary())
}