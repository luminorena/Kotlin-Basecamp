package ru.basecamp.library.io

class BookInput {
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
}