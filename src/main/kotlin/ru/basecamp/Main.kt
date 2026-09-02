package ru.basecamp

import ru.basecamp.io.LoanResult
import ru.basecamp.model.Book
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

    println("Всего: ${library.size}")
    println("По ISBN 7685916719993: ${library.findByIsbn("7685916719993")?.title}")

    library.place(book1, 0, 0)
    library.place(book2, 0, 1)
    library.printShelves()
    println("\nПо жанрам:")

    for ((genre, list) in library.byGenre()) {
        println("${genre.displayName}: ${list.joinToString { it.title }}")
    }

    println("\nТоп по выдачам:")
    for (b in library.topByLoans(3)) {
        println("- ${b.title} (${b.totalLoans} выдач)")
    }

    println("\nТоп самых толстых PrintedBook:")
    for (b in library.topThickest(3)) {
        println("- ${b.title} (${b.pages} стр.)")
    }

    println("\nТоп авторов:")
    for ((author, count) in library.topAuthors(3)) {
        println("- $author: $count книг")
    }

    val printed = library.books.filterIsInstance<PrintedBook>()
    val thinnest = printed.min()
    val thickest = printed.max()
    val sorted = printed.sorted()

    val multisorted = library.all().sortedWith(
        compareBy<Book> { it.genre.name }
            .thenByDescending { it.year }
            .thenBy { it.title }
    )

    println("Печатная книга ${printed}")
    println("Самая тонкая книга ${thinnest}")
    println("Самая толстая книга ${thickest}")
    println("По возрастанию страниц ${sorted}")
    println("Многокритериальная сортировка ${multisorted}")

    println("Общая стоимость каталога: ${library.totalCatalogValue()}")

    library.reserve("Аня", book1)
    library.reserve("Боря", book2)
    println("Очередь: ${library.queueSize()}")
    val next = library.nextReservation()
    println("Следующий — ${next?.first} получит «${next?.second?.title}»")
}