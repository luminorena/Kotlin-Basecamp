package ru.basecamp

import ru.basecamp.io.LoanResult
import ru.basecamp.model.Genre
import ru.basecamp.model.Library
import ru.basecamp.model.Money
import ru.basecamp.model.PrintedBook

fun main() {

    val library = Library("Моя библиотека")

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

    for ((genre, booksOfGenre) in library.byGenre()) {
        println("${genre.displayName}: ${booksOfGenre.size} книг")
        for (b in booksOfGenre) println(" - ${b.title}")
    }

    library.addBook(book1)
    library.addBook(book2)

    println(library.all())

    println("Всего: ${library.size}")
    println("По ISBN 7685916719993: ${library.findByIsbn("7685916719993")?.title}")
    library.place(book1, 0, 0)
    library.place(book2, 0, 1)
    library.printShelves()
    println("\nПо жанрам:")
    for ((genre, list) in library.byGenre()) {
        println("${genre.displayName}: ${list.joinToString { it.title }}")
    }
}