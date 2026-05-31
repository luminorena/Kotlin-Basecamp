package ru.otus.ru.otus.task_01

/**
 *  1. Задача 1. От хардкода — к переменным.
 *        Возьмите карточку книги из ДЗ #1 и перепишите:
 *          - Каждое поле — отдельная переменная (val, не var — ничего не меняем).
 *          - Имена в camelCase: title, author, year, pages, price, copiesInStock.
 *          - Вывод — через строковые шаблоны, не конкатенацией:
 *              println("Название: $title")
 *              println("Цена: $price руб.")
 *          - Сумма копий и общая стоимость экземпляров на складе —
 *           посчитайте через выражение в шаблоне:
 *           "Общая стоимость на складе: ${price * copiesInStock} руб."
 */
fun main() {
    val user = "Olga"
    val bookName = "Над пропастью во ржи"
    val author = "Джером Дэвид Сэлинджер"
    val originalName = "The Catcher in the Rye"
    val genre = "Роман"
    val sourceLanguage = "английский"
    val publisher = "Little, Brown and Company"
    val pages = 213
    val price = 619.00
    val copiesInStock = 50
    val isbn = "978-5-699-30534-6"

    println("Добро пожаловать в библиотеку $user!")
    println("Название книги: $bookName")
    println("Автор: $author")
    println("Оригинальное название: $originalName")
    println("Жанр: $genre")
    println("Язык оригинала: $sourceLanguage")
    println("Издательство: $publisher")
    println("Количество страниц: $pages")
    println("Цена в розницу: $price")
    println("ISBN: $isbn")

    println("Общая стоимость на складе: ${price * copiesInStock} руб.")
}
