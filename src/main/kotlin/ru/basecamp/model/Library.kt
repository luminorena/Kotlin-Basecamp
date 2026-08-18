package ru.basecamp.model

class Library(val name: String, rows: Int = 5, cols: Int = 10) {
    private val books = mutableListOf<Book>()
    private val byIsbn = mutableMapOf<String, Book>()
    private val shelves: Array<Array<String?>> = Array(rows) { arrayOfNulls(cols) }

    private val byAuthorYear: MutableMap<Pair<String, Int>, Book> = mutableMapOf()

    fun find(author: String, year: Int): Book? = byAuthorYear[author to year]

    fun addBook(book: Book) {
        books.add(book)
        book.isbn?.let { isbn ->
            require(isbn !in byIsbn) { "Книга с ISBN $isbn уже есть в каталоге" }
            byIsbn[isbn] = book
        }
    }

    fun addBookByAuthorYear(book: Book){
        byAuthorYear[book.author to book.year] = book
    }
    fun findByIsbn(isbn: String): Book? = byIsbn[isbn]
    fun hasIsbn(isbn: String): Boolean = isbn in byIsbn
    fun removeBook(book: Book): Boolean = books.remove(book)
    val size: Int get() = books.size
    fun all(): List<Book> = books.toList()
    override fun toString(): String = "Библиотека «$name» ($size книг)"

    fun byGenre(): Map<Genre, List<Book>> {
        val result = mutableMapOf<Genre, MutableList<Book>>()
        for (book in books) {
            val list = result.getOrPut(book.genre) { mutableListOf() }
            list.add(book)
        }
        return result
    }

    fun place(book: Book, row: Int, col: Int): Boolean {
        require(row in shelves.indices && col in shelves[row].indices)
        if (shelves[row][col] != null) return false
        shelves[row][col] = book.isbn ?: book.title
        return true
    }
    fun printShelves() {
        for ((rowIdx, row) in shelves.withIndex()) {
            print("Ряд $rowIdx: ")
            for (cell in row) print(if (cell == null) ". " else "□ ")
            println()
        }
    }
}