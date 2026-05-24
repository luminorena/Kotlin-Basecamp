package ru.otus

/**
 * ДЗ #1
 *
 *    1. Программа должна вывести:
 *       а) Строку приветствия пользователя библиотеки
 *       б) Карточку первой книги в указанном формате
 *    2. Использовать правильные типы для каждого поля
 *       (цена — Double, год и количество — Int).
 *    3. ★ Бонус (опционально): функция showLiterals()
 *       с экспериментами по числовым нотациям.
 */
fun main() {
    val user: String = "Olga"
    val bookName: String = "Над пропастью во ржи"
    val author: String = "Джером Дэвид Сэлинджер"
    val originalName: String = "The Catcher in the Rye"
    val genre: String = "Роман"
    val sourceLanguage: String = "английский"
    val publisher: String = "Little, Brown and Company"
    val pages: Int = 213
    val isbn: String = "978-5-699-30534-6"

    println("Добро пожаловать в библиотеку $user!")
    println("Название книги: $bookName")
    println("Автор: $author")
    println("Оригинальное название: $originalName")
    println("Жанр: $genre")
    println("Язык оригинала: $sourceLanguage")
    println("Издательство: $publisher")
    println("Количество страниц: $pages")
    println("ISBN: $isbn")

    println(showLiterals())
}

fun showLiterals() {
    val intNumber: Int = 1_000_000
    val hexNumber: Int = 0xCAFE
    val binaryNumber: Int = 0b1010_1010
    val doubleNumber: Double = 1.5e3

    println("Миллион с подчёркиваниями: $intNumber")
    println("Десятичное представление hex: $hexNumber")
    println("Десятичное представление binary: $binaryNumber")
    println("Стандартная запись дробного числа: $doubleNumber")
}