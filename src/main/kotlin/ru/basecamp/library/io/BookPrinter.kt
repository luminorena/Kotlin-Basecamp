package ru.basecamp.library.io

class BookPrinter(val title: String, val author: String, val year: Int, val pages: Int, val price: Double, val initialCopies: Int) {
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

    fun printBookCard(isbn: String? = null,
                      originalLanguage: String? = null,
                      translator: String? = null,
                      edition: String? = null,
                      withFancyFrame: Boolean = false) {

        val isbn = isbn?.let { "ISBN: $isbn" } ?: ""
        val language = originalLanguage?.let { "Язык оригинала: $originalLanguage" } ?: ""
        val translator = translator?.let { "Переводчик: $translator" } ?: ""
        val edition = edition?.let { "Издательство: $edition" } ?: ""

        if (withFancyFrame) {
            val width = 46

            println("=".repeat(width))
            println("║ ${"Карточка книги:".padEnd(width - 4)} ║")
            println("║ ${"Название: $title".padEnd(width - 4)} ║")
            println("║ ${"Автор: $author".padEnd(width - 4)} ║")
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
           Цена: $price
           Количество: $copiesInStock
           $isbn
           $language
           $translator
           $edition
           """.trimIndent()
                .replace(Regex("\n{2,}"), "\n"))
    }

    fun lend(): Boolean {
        if (copiesInStock <= 0) return false
        copiesInStock--
        totalLoans++
        return true
    }
    fun returnCopy() {
        copiesInStock++
    }
}