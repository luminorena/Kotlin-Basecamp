package ru.basecamp.task_02

import ru.basecamp.task_03.digitsSum

fun main() {
    printBookCard(
        title = "Война и мир",
        author = "Лев Толстой",
        year = 1869,
        pages = 1225,
        price = 799.90,
        quantity = 3,
        isbn = 1212
    )

    printBookCard(
        title = "Война и мир",
        author = "Лев Толстой",
        year = 1869,
        pages = 1225,
        price = 799.90,
        quantity = 3,
        isbn = 3453,
        withFancyFrame = true
    )
}

fun printBookCard(
    title: String,
    author: String,
    year: Int,
    pages: Int,
    price: Double,
    quantity: Int,
    isbn: Long,
    withFancyFrame: Boolean = false
) {
    if (withFancyFrame) {
        val width = 46
        val contentWidth = width - 4

        println("╔" + "═".repeat(width - 2) + "╗")
        println("║ ${"Карточка книги:".padEnd(contentWidth)} ║")
        println("║ ${"Название: $title".padEnd(contentWidth)} ║")
        println("║ ${"Автор: $author".padEnd(contentWidth)} ║")
        println("║ ${"Год: $year".padEnd(contentWidth)} ║")
        println("║ ${"Количество страниц: $pages".padEnd(contentWidth)} ║")
        println("║ ${"Цена: $price".padEnd(contentWidth)} ║")
        println("║ ${"Количество: $quantity".padEnd(contentWidth)} ║")
        println("║ ${"Сумма цифр ISBN: ${digitsSum(isbn)}".padEnd(contentWidth)} ║")
        println("╚" + "═".repeat(width - 2) + "╝")
    } else {
        println(
            """
            Карточка книги:
            Название: $title
            Автор: $author
            Год: $year
            Количество страниц: $pages
            Цена: $price
            Количество: $quantity
            Сумма цифр ISBN: ${digitsSum(isbn)}
            """.trimIndent()
        )
    }
}