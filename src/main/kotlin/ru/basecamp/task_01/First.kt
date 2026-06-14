package ru.basecamp.task_01

/**
 * Поле Тип Что значит null
 * originalLanguage: String? язык оригинала книга оригинальная (не перевод)
 * translator: String? переводчик книга оригинальная
 * edition: Int? номер издания не указано
 * isbn: String? ISBN у книги нет ISBN (например,
 * самиздат)
 * Задача 1. Опциональные поля в карточке
 * Расширьте модель книги:
 * В функции readBookData():
 * q Спрашивайте поля, при пустом вводе сохраняйте null:
 *
 * print("Язык оригинала (Enter если оригинал на русском): ")
 * val originalLanguage = readln().ifBlank { null }
 *
 * q edition — readln().toIntOrNull() (вернёт null для нечисел и пустоты).
 * q isbn — пусто или невалидный - null.
 * (используйте cleanIsbn + isValidIsbn из ДЗ #4.)
 *
 */

private val CLEAN_REGEX = Regex("[\\s-]")
private val DIGITS_REGEX = Regex("\\d+")

fun main() {
    readBookData()
    println(cleanIsbn(null))
}

fun readBookData(): List<String?> {
    val bookData = mutableListOf<String?>()
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

    print("Введите язык оригинала")
    val originalLanguage: String? = readlnOrNull()
    print("Введите ФИО переводчика")
    val translator: String? = readlnOrNull()
    print("Введите номер издания:")
    val edition: Int? = readln().toIntOrNull()
    print("Введите ISBN")
    val isbn: String? = readlnOrNull()
    bookData.add(originalLanguage)
    bookData.add(translator)
    bookData.add(edition.toString())
    bookData.add(isbn)

    bookData.add(title)
    bookData.add(author)
    bookData.add(year.toString())
    bookData.add(pages.toString())
    bookData.add(price.toString())
    bookData.add(quantity.toString())

    return bookData
}

fun cleanIsbn(rawIsbn: String?): String {
    val cleanIsbn = rawIsbn?.replace(CLEAN_REGEX, "")

    if (cleanIsbn?.length != 13 || !cleanIsbn.matches(DIGITS_REGEX)) {
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