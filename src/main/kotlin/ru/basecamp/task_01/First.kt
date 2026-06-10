package ru.basecamp.task_01

import java.util.Locale.getDefault

/**
 * Задача 1. Декомпозиция main()
 * Разбейте код на функции с понятными именами и сигнатурами. Как минимум:
 * Требования:
 * • Однострочные функции — где это естественно (formatPrice, categorizePages).
 * • Полные функции с телом — где логика сложнее.
 * • main() после рефакторинга должен помещаться примерно в 15 строк
 * и читаться как сценарий: «прочитать - провалидировать - напечатать -
 * симулировать».
 * • Везде, где функция возвращает значение, — возвращайте его,
 * а не печатайте внутри. Печать — отдельно. (Принцип «команда vs. запрос».)
 * 💡 На этом этапе у вас ещё нет классов и data-классов.
 * Книгу можно «таскать» как набор отдельных параметров — это специально неудобно,
 * чтобы в ДЗ #7 вы сказали: «о, теперь нам нужен класс».
 * fun readBookData(): /* кортеж/объект с полями книги — пока несколько отдельных
 * readln() в main */
 * fun cleanIsbn(rawIsbn: String): String +
 * fun isValidIsbn(isbn: String): Boolean +
 * fun validateBook(title: String, year: Int, pages: Int, price: Double, copies: Int): List<String> +
 * fun categorizePages(pages: Int): String +
 * fun formatPrice(price: Double): String // "%.2f руб." +
 * fun makeShortTitle(title: String, maxLen: Int = 30): String +
 * fun makeAuthorInitials(fullName: String): String +
 * fun printBookCard(/* параметры */)
 * fun simulateLoans(copies: Int, requestedLoans: Int) +
 */

private val CLEAN_REGEX = Regex("[\\s-]")
private val DIGITS_REGEX = Regex("\\d+")

private const val VALIDATE_ERROR = "Число страниц должно быть больше нуля"
private const val BROCHURE = "Брошюра"
private const val STANDARD_BOOK = "Стандартная книга"
private const val THICK_BOOK = "Толстая книга"
private const val VERY_THICK_BOOK = "Очень толстая книга"
private const val ENORMOUS_BOOK = "Кирпич"

fun main() {
    readBookData()
    cleanIsbn("1234556789543")
    validateBook("Над пропастью во ржи", 1990, 200, 500.50, 40)
    simulateLoans(120, 10)
    makeAuthorInitials("Лев Николаевич Толстой")
    printBookCard("Война и мир", "Лев Толстой", 1869, 1225, 799.90, 3)
    makeShortTitle("Война и мир")
    categorizePages(500)
    formatPrice(3467.0)
}

fun readBookData(): List<String> {
    val bookData = mutableListOf<String>()
    print("Введите название книги: ")
    val title: String = readln()
    print("Введите автора: ")
    val author: String = readln()
    print("Введите год издания: ")
    val year: Int = readln().toInt()
    print("Введите количество страниц: ")
    val pages: Int = readln().toInt()
    print("Введите цену (руб.): ")
    val price: Double = readln().toDouble()
    print("Введите количество экземпляров: ")
    val quantity: Int = readln().toInt()

    bookData.add(title)
    bookData.add(author)
    bookData.add(year.toString())
    bookData.add(pages.toString())
    bookData.add(price.toString())
    bookData.add(quantity.toString())

    return bookData
}

fun cleanIsbn(rawIsbn: String): String {
    val cleanIsbn = rawIsbn.replace(CLEAN_REGEX, "")

    if (cleanIsbn.length != 13 || !cleanIsbn.matches(DIGITS_REGEX)) {
        return "ISBN должен быть равен 13-и символам и содержать только цифры"
    }

    var result = 0
    for (i in cleanIsbn.indices) {
        result += if (i % 2 == 0) {
            cleanIsbn[i].digitToInt() * 1
        }
        else {
            cleanIsbn[i].digitToInt() * 3
        }
    }

    if (result % 10 == 0) println("ISBN валиден, контрольная сумма равна $result")
    else println("ISBN не валиден, контрольная сумма равна $result, попробуйте ещё раз")

    return cleanIsbn
}

fun validateBook(title: String, year: Int, pages: Int, price: Double, copiesInStock: Int): List<String> {
    val errorsList = mutableListOf<String>()

    if (year !in 1450..2026) errorsList.add(year.toString())
    if (pages !in 1..10_000) errorsList.add(pages.toString())
    if (price < 0) errorsList.add(price.toString())
    if (copiesInStock < 0) errorsList.add(copiesInStock.toString())
    if (title.isEmpty()) errorsList.add(title)

    return errorsList
}

fun categorizePages(pages: Int): String {
    return when {
        pages <=0 -> VALIDATE_ERROR
        pages < 50 -> BROCHURE
        pages in 1..199 -> STANDARD_BOOK
        pages in 200..499 -> THICK_BOOK
        pages in 500..999 -> VERY_THICK_BOOK
        else -> ENORMOUS_BOOK
    }
}

fun makeShortTitle(title: String, maxLen: Int = 30): String = if (title.length > maxLen) title.take(maxLen) + "..." else title

fun formatPrice(price: Double): String = String.format("%.2f", price)

fun simulateLoans(copies: Int, requestedLoans: Int) {
    var totalLoans = 0
    var currentlyOnHand = copies

    println("Сколько раз выдать книгу ?")
    val issuesQuantity: Int = readln().toInt()

    for(i in 1..issuesQuantity) {
        if (currentlyOnHand < requestedLoans) {
            currentlyOnHand++
            totalLoans++
            println("Выдача $i: на руках $currentlyOnHand из $requestedLoans")
        }
        else {
            println("Выдача $i: отказ — все экземпляры заняты")
            break
        }
    }

    val freeBooks = requestedLoans - currentlyOnHand

    println("""
        Финальная статистика: всего выдач $totalLoans, на руках находятся $currentlyOnHand книг,
        свободно $freeBooks книг
        """)
}

fun makeAuthorInitials(fullName: String): String {
    var res = ""
    val split = fullName.split(" ")
    for (i in split.indices) {
        val word = split[i]
        res = if (i == split.lastIndex) word
        else word.take(1).uppercase(getDefault()) + ". "
    }
    return res
}

fun printBookCard(title: String, author: String, year: Int, pages: Int, price: Double, quantity: Int) {
    println("""
           Карточка книги:
           Название: $title,
           Автор:$author,
           Год: $year,
           Количество страниц: $pages,
           Цена: $price,
           Количество: $quantity
           """)
}