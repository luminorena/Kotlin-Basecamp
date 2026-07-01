package ru.basecamp.model

open class Book(
    val title: String,
    val author: String,
    val year: Int,
    val pages: Int,
    val price: Double,
    val initialCopies: Int,
    val isbn: String? = null,
    val edition: String? = null,
    val originalLanguage: String? = null,
    val translator: String? = null,
    val genre: Genre? = null,
) {
    var copiesInStock: Int = initialCopies
        protected set
    var totalLoans: Int = 0
        protected set
    var totalPages: Int = 0
        protected set

    init {
        require(title.isNotBlank()) { "Название не может быть пустым" }
        require(author.isNotBlank()) { "Автор не может быть пустым" }
        require(year in 1450..2100) { "Год $year вне допустимого диапазона" }
        require(pages > 0) { "Страниц должно быть положительно, а не $pages" }
        require(price >= 0) { "Цена не может быть отрицательной" }
        require(initialCopies >= 0) { "Количество экземпляров не может быть отрицательным" }
    }

    constructor(title: String, author: String, year: Int, pages: Int, price: Double, copies: Int, genre: Genre) :
            this(
                title, author, year, pages, price, copies,
                isbn = null, edition = null, originalLanguage = null, translator = null, genre
            )

    open val isAvailable: Boolean
        get() = copiesInStock > 0

    open fun printBookCard(
        isbn: String? = null,
        originalLanguage: String? = null,
        translator: String? = null,
        edition: String? = null,
        withFancyFrame: Boolean = false
    ) {
    }

    open fun matches(query: String): Boolean =
        title.contains(query, ignoreCase = true) ||
                author.contains(query, ignoreCase = true)

    open fun lend(): Boolean {
        if (copiesInStock <= 0) return false
        copiesInStock--
        totalLoans++
        return true
    }
}