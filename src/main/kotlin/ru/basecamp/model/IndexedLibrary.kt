package ru.basecamp.model

class IndexedLibrary(val name: String) :
    MutableMap<String, Book> by mutableMapOf() {
    fun addBook(book: Book) {
        val isbn = book.isbn ?: error("Книгу без ISBN запрещено добавлять в библиотеку")
        require(isbn !in this) { "Данный ISBN уже существует: $isbn" }
        this[isbn] = book
    }
    fun byAuthor(author: String): List<Book> =
        values.filter { it.author == author }
}