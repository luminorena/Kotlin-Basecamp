import ru.basecamp.exception.BookAlreadyExistsException
import ru.basecamp.exception.BookNotFoundException
import ru.basecamp.model.PrintedBook

class Library(
    val name: String,
    private val books: MutableList<PrintedBook> = mutableListOf()
) {
    val size: Int
        get() = books.size

    fun findByIsbn(isbn: String?): PrintedBook? {
        val normalizedIsbn = isbn?.trim() ?: return null
        if (normalizedIsbn.isEmpty()) return null
        return books.firstOrNull { it.isbn == normalizedIsbn }
    }

    fun addBook(book: PrintedBook) {
        val isbn = book.isbn ?: return
        if (findByIsbn(isbn) != null) {
            throw BookAlreadyExistsException(isbn)
        }
        books.add(book)
    }

    fun getByIsbn(isbn: String): PrintedBook =
        findByIsbn(isbn) ?: throw BookNotFoundException(isbn)

    fun search(predicate: (PrintedBook) -> Boolean): List<PrintedBook> =
        books.filter(predicate)
}