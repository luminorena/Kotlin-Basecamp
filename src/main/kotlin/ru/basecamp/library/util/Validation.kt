package ru.basecamp.library.util

import java.math.BigInteger

private val CLEAN_REGEX = Regex("[\\s-]")
private val DIGITS_REGEX = Regex("\\d+")

class Validation(val title: String, val author: String, val year: Int, val pages: Int, val price: Double, val initialCopies: Int) {
    var copiesInStock: Int = initialCopies
        private set
    var totalLoans: Int = 0
        private set
    var totalPages: Int = 0
        private set
    init {
        require(title.isNotBlank()) { "Название не может быть пустым" }
        require(author.isNotBlank()) { "Автор не может быть пустым" }
        require(year in 1450..2100) { "Год $year вне допустимого диапазона" }
        require(pages > 0) { "Страниц должно быть положительно, а не $pages" }
        require(price >= 0) { "Цена не может быть отрицательной" }
        require(initialCopies >= 0) { "Количество экземпляров не может быть отрицательным" }
    }

    val isAvailable: Boolean
        get() = copiesInStock > 0

    val shortTitle: String
        get() = if (title.length > 30) title.take(27) + "..." else title


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

    fun validateBook(): List<String> {
        val errorsList = mutableListOf<String>()

        if (year !in 1450..2026) errorsList.add(year.toString())
        if (pages !in 1..10_000) errorsList.add(pages.toString())
        if (price < 0) errorsList.add(price.toString())
        if (copiesInStock < 0) errorsList.add(copiesInStock.toString())
        if (title.isEmpty()) errorsList.add(title)

        return errorsList
    }

    tailrec fun digitsSumTail(n: BigInteger, acc: Int = 0): Int {
        val ten = BigInteger.TEN
        return if (n < ten) acc + n.toInt()
        else digitsSumTail(n / ten, acc + (n % ten).toInt())
    }
}

