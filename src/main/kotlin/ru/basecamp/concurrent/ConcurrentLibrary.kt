package ru.basecamp.concurrent

import ru.basecamp.io.LoanResult
import ru.basecamp.model.Book
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class ConcurrentLibrary(val name: String) {
    private val books: MutableList<Book> = Collections.synchronizedList(mutableListOf())
    private val byIsbn: ConcurrentHashMap<String, Book> = ConcurrentHashMap()
    private val totalLoans = AtomicInteger(0)
    @Synchronized
    fun addBook(book: Book) {
        book.isbn?.let { isbn ->
            require(byIsbn.putIfAbsent(isbn, book) == null) { "Duplicate ISBN: $isbn" }
        }
        books.add(book)
    }
    fun findByIsbn(isbn: String): Book? = byIsbn[isbn]
    @Synchronized
    fun lend(isbn: String): LoanResult {
        val book = byIsbn[isbn] ?: return LoanResult.BookNotInLibrary(isbn)
        val result = book.lend()
        if (result is LoanResult.Success) totalLoans.incrementAndGet()
        return result
    }
    fun stats(): String = "Книг: ${books.size}, выдач: ${totalLoans.get()}"
}