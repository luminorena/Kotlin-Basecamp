package ru.basecamp.io

import ru.basecamp.model.Book

class Helper : Book(
    "Default Title",
    "Default Author",
    2000,
    100,
    1000.0,
    1
) {

    fun main() {
        val lib = Library()
        lib.add(Book("Crime and Punishment", "Fyodor Dostoevsky", 1866, 671, 9.99, 3, "9785916719892"))

        val newBook = PrintedBook("Test", "Тест", 2020, 200, 100.00, 20, "1234567890").apply {
            println("Создана: $title, $author")
        }

        val book = lib.findByIsbn("9785916719892")?.also {
            println("Найдена книга: ${it.title}")
        }

        val titleLength: Int? = lib.findByIsbn("9785916719892")?.let { it.title.length }

        val description = lib.run {
            val isAvailable = isAvailable
            val isMatched = matches("преступление")

            "Наличие книги по запросу $isMatched, книга доступна $isAvailable "
        }
        println(description)

        val cardText = with(book) {
            """
    $title
    $author, $year
    Цена: $price
    """.trimIndent()
        }

        val foundBook = lib.findByIsbn("9785916719892")?.let {
            println("Найдена книга: ${it.title}")
            it
        }
    }
}