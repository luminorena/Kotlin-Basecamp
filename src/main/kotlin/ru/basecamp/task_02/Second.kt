package ru.basecamp.task_02

/**
 * Задача 2. Default и named аргументы
 * В функции printBookCard сделайте параметр withFancyFrame: Boolean = false. Если true — карточка обрамляется
 * псевдографикой:
 * Если false — обычные === как раньше.
 * Вызовите её дважды в main():
 * Обратите внимание: именованный аргумент withFancyFrame = — обязателен, иначе непонятно, где какой
 * Boolean/String (когда параметров много).
 */

fun main() {
    printBookCard("Война и мир", "Лев Толстой", 1869, 1225, 799.90, 3, true)
    printBookCard("Война и мир", "Лев Толстой", 1869, 1225, 799.90, 3, false)
}

fun printBookCard(title: String, author: String, year: Int, pages: Int, price: Double, quantity: Int, withFancyFrame: Boolean) {
    if (withFancyFrame) {
        val width = 46

        println("=".repeat(width))
        println("║ ${"Карточка книги:".padEnd(width - 4)} ║")
        println("║ ${"Название: $title".padEnd(width - 4)} ║")
        println("║ ${"Автор: $author".padEnd(width - 4)} ║")
        println("║ ${"Год: $year".padEnd(width - 4)} ║")
        println("║ ${"Количество страниц: $pages".padEnd(width - 4)} ║")
        println("║ ${"Цена: $price".padEnd(width - 4)} ║")
        println("║ ${"Количество: $quantity".padEnd(width - 4)} ║")
        println("=".repeat(width))
    }
    else
        println("""
           Карточка книги:
           Название: $title,
           Автор:$author,
           Год: $year,
           Количество страниц: $pages,
           Цена: $price,
           Количество: $quantity
           """.trimIndent())
}