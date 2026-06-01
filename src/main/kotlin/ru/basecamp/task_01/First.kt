package ru.basecamp.task_01

/**
 * После считывания данных книги (из ДЗ #2) добавьте блок проверок.
 * Программа должна вывести список
 * проблем, если они есть, и в конце — итоговый статус:
 *
 * year от 1450 (примерно изобретение печатного станка) до текущего года
 * pages от 1 до 10_000
 * price положительная (> 0)
 * copiesInStock неотрицательное (>= 0)
 *
 * Используйте:
 * •проверку диапазона через in 1..10_000 (а не >= 1
 * && <= 10_000)
 * •if или when — на ваш выбор, но подумайте, где какой
 * выразительнее
 *
 * Если все проверки прошли — Итог: принято в каталог.
 */
fun main() {

    print("Введите название книги: ")
    val bookName: String = readln()
    print("Введите автора: ")
    val author: String = readln()
    print("Введите год издания: ")
    val year: Int = readln().toInt()
    print("Введите количество страниц: ")
    val pages: Int = readln().toInt()
    print("Введите цену (руб.): ")
    val price: Double = readln().toDouble()
    print("Введите количество экземпляров на складе: ")
    val copiesInStock: Int = readln().toInt()

    println("Название книги: $bookName")
    println("Автор: $author")
    println("Год издания: $year")
    println("Количество страниц: $pages")
    println("Цена: $price")
    println("Количество экземпляров: $copiesInStock")

    println("-".repeat(15))
    var problemsQuantity = 0

    if (year in 1450..2026) {
        println("Год: $year")
    } else {
        println("Год $year находится вне диапазона 1450..2026")
        problemsQuantity++
    }

    if (pages in 1..10_000) {
        println("Страниц: $pages")
    } else {
        println("Страницы $pages находятся вне диапазона 1..10_000")
        problemsQuantity++
    }

    if (price > 0) {
        println("Цена: $price")
    } else {
        println("Цена $price должна быть больше нуля")
        problemsQuantity++
    }

    if (copiesInStock >= 0) {
        println("Количество: $copiesInStock")
    } else {
        println("Количество $copiesInStock должно быть больше или равно нулю")
        problemsQuantity++
    }

    if (problemsQuantity == 0)
        println("Итог: принято в каталог.")
    else println("Итог: НЕ принято в каталог (количество найденных проблем: $problemsQuantity)")
}