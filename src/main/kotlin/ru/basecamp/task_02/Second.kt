package ru.basecamp.task_02

fun main() {
    printBookCard("Война и мир", "Лев Толстой", 1869, 1225, 799.90, 3, edition = 1234)

    printBookCard("Преступление и наказание", "Фёдор Достоевский", 1866, 671, 649.00, 5)
}

fun printBookCard(
    title: String,
    author: String,
    year: Int,
    pages: Int,
    price: Double,
    quantity: Int,
    isbn: String? = null,
    originalLanguage: String? = null,
    translator: String? = null,
    edition: Int? = null
) {
    println("Карточка книги:")
    println("Название: $title")
    println("Автор: $author")
    println("Год: $year")
    println("Количество страниц: $pages")
    println("Цена: $price")
    println("Количество: $quantity")
    isbn?.let { println("ISBN: $it") }
    originalLanguage?.let { println("Язык оригинала: $it") }
    translator?.let { println("Переводчик: $it") }
    edition?.let { println("Издание: $it") }
}