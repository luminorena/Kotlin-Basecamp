package ru.basecamp.model

import ru.basecamp.io.LoanResult

class PrintedBook(
    title: String, author: String, year: Int, pages: Int, genre: Genre, price: Money,
    initialCopies: Int, isbn: String
) : Book(title, author, year, pages, genre, price, initialCopies, isbn) {
    override val category = "Печатная книга"
    init { require(pages > 0) { "Страниц должно быть положительно" } }
    override fun printBookCard(
        isbn: String?,
        originalLanguage: String?,
        translator: String?,
        edition: String?,
        withFancyFrame: Boolean
    ) {
        printBookCard(isbn, originalLanguage, translator, edition, withFancyFrame)

        if (tags.isNotEmpty()) println("Тэги: ${tags.joinToString(", ")}")

        if (!isValidYear(year)) {
            println("Некорретный год книги $year")
        }

        if (withFancyFrame) {
            val width = 46

            println("=".repeat(width))
            println("║ ${"Карточка книги:".padEnd(width - 4)} ║")
            println("║ ${"Название: $title".padEnd(width - 4)} ║")
            println("║ ${"Автор: $author".padEnd(width - 4)} ║")
            println("║ ${"Жанр: ${genre.emoji} ${genre.displayName} ║".padEnd(width - 4)}")
            println("║ ${"Год: $year".padEnd(width - 4)} ║")
            println("║ ${"Количество страниц: $pages".padEnd(width - 4)} ║")
            println("║ ${"Цена: $price".padEnd(width - 4)} ║")
            println("║ ${"Количество: $copiesInStock".padEnd(width - 4)} ║")
            println("=".repeat(width))
        } else
            println("""
           Карточка книги:
           Название: $title
           Автор:$author
           Год: $year
           Количество страниц: $pages
           Жанр: $genre
           Цена: $price
           Количество: $copiesInStock
           $isbn
           $originalLanguage
           $translator
           $edition
           """.trimIndent()
                .replace(Regex("\n{2,}"), "\n"))
    }

    override fun returnCopy() {
        copiesInStock++
    }

    override fun lend(): LoanResult {
        return super.lend()
    }
}