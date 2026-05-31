package ru.otus.ru.otus.task_04

/**
 *    Задача 4. Форматирование цены.
 *      Цена выводится как 1290.5, а должно быть 1290.50
 *      (две цифры после запятой).
 *      Используйте "%.2f".format(price) или String.format(...).
 */
fun main() {
    print("Введите цену (руб.): ")
    val price: Double = readln().toDouble()

    println("Цена: " + String.format("%.2f", price))
}