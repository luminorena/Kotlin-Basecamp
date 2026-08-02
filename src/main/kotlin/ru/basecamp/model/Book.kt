package ru.basecamp.model

import ru.basecamp.io.LoanResult

abstract class Book(
    val title: String,
    val author: String,
    val year: Int,
    val pages: Int,
    val genre: Genre = Genre.OTHER,
    val price: Money,
    val initialCopies: Int,
    val isbn: String? = null,
    val edition: String? = null,
    val originalLanguage: String? = null,
    val translator: String? = null
) {

    companion object {
        const val MIN_YEAR = 1450
        const val MAX_YEAR = 2100
        fun isValidYear(year: Int): Boolean = year in MIN_YEAR..MAX_YEAR
    }

    var copiesInStock: Int = initialCopies
        protected set
    var totalLoans: Int = 0
        protected set
    var totalPages: Int = 0
        protected set
    abstract val category: String

    init {
        require(title.isNotBlank()) { "Название не может быть пустым" }
        require(author.isNotBlank()) { "Автор не может быть пустым" }
        require(year in 1450..2100) { "Год $year вне допустимого диапазона" }
        require(pages > 0) { "Страниц должно быть положительно, а не $pages" }
        require(price.amount >= 0) { "Цена не может быть отрицательной" }
        require(initialCopies >= 0) { "Количество экземпляров не может быть отрицательным" }
    }

    constructor(title: String, author: String, year: Int, pages: Int, genre: Genre, price: Money, copies: Int) :
            this(title, author, year, pages, genre, price, copies,
                isbn = null, edition = null, originalLanguage = null, translator = null)

    val isAvailable: Boolean
        get() = copiesInStock > 0

    fun matches(query: String): Boolean =
        title.contains(query, ignoreCase = true) ||
                author.contains(query, ignoreCase = true)

    open fun returnCopy(){}

    open fun printBookCard(isbn: String? = null,
                           originalLanguage: String? = null,
                           translator: String? = null,
                           edition: String? = null,
                           withFancyFrame: Boolean = false) {
    }

    open fun lend(): LoanResult {
        if (copiesInStock <= 0) return LoanResult.NotAvailable(0)
        copiesInStock--
        totalLoans++
        return LoanResult.Success
    }
}