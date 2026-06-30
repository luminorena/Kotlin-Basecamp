package ru.basecamp.library

import ru.basecamp.library.io.AudioBook
import ru.basecamp.library.io.EBook
import ru.basecamp.library.io.PrintedBook
import ru.basecamp.library.model.Book
import ru.basecamp.library.model.Loanable

fun main() {
    val isbn = "978-0132350884"
    val originalLanguage = "English"
    val translator = "Переводчик"
    val edition = "1-е издание"
    val withFancyFrame = false

    val items: List<Loanable> = listOf(
        PrintedBook(
            "Чистый код", "Р. Мартин",  2008,  464,  1290.0, 3, "978-0132350884"),
        EBook("Kotlin in Action", "Д. Жемеров", 2017, price = 990.0, sizeMb = 12.5, format = "PDF"),
        AudioBook(
            "Гарри Поттер",
            "Дж. Роулинг",
            1997,
            599.0,
            initialCopies = 2,
            durationMinutes = 480,
            narrator = "С. Чонишвили"
        ),
    )
    for (item in items) {
        if (item is Book) item.printBookCard(isbn, originalLanguage, translator, edition, withFancyFrame)
        println("Статус: ${item.describeAvailability()}")
        item.lend()
        println("После выдачи: ${item.describeAvailability()}")
        println("---")
    }
}