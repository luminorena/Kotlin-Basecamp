package ru.basecamp.model

import ru.basecamp.io.LoanResult
import ru.basecamp.util.Memoized
import java.lang.reflect.Member
import java.util.HashMap

class Library(val name: String, rows: Int = 5, cols: Int = 10) {
    val books = HashMap<String, Book>()
    private val loans = HashMap<Book, Member>()
    private val byIsbn = HashMap<String, Book>()
    private val shelves: Array<Array<String?>> = Array(rows) { arrayOfNulls(cols) }

    private val byAuthorYear: MutableMap<Pair<String, Int>, Book> = HashMap()
    lateinit var defaultLoanPolicy: LoanPolicy

    fun lend(book: Book, member: Member): LoanResult {
        check(::defaultLoanPolicy.isInitialized) {
            "Loan policy not configured"
        }

        val isbn = book.isbn
            ?: return LoanResult.BookNotInLibrary(null)

        val libraryBook = books[isbn]
            ?: return LoanResult.BookNotInLibrary(isbn)

        val loanedCopies = loans.keys.count { it.isbn == isbn }
        val available = libraryBook.initialCopies - loanedCopies

        if (available <= 0) {
            return LoanResult.NotAvailable(available)
        }

        val activeLoans = loans.values.count { it == member }

        if (!defaultLoanPolicy.canLend(member, book, activeLoans)) {
            return LoanResult.TooManyOnHand(defaultLoanPolicy.maxBooksPerMember)
        }

        loans[book] = member
        return LoanResult.Success
    }


    fun find(author: String, year: Int): Book? = byAuthorYear[author to year]

    fun addBook(book: Book) {
        book.isbn?.let { isbn ->
            if (isbn in byIsbn) {
                throw IllegalArgumentException("Книга с ISBN $isbn уже есть в каталоге")
            }

            byIsbn[isbn] = book
            books[isbn] = book
        }
    }

    private val reservationQueue: ArrayDeque<Pair<String, Book>> = ArrayDeque()
    fun reserve(userName: String, book: Book) {
        reservationQueue.addLast(userName to book)
        println("$userName поставлен в очередь на «${book.title}»")
    }

    fun nextReservation(): Pair<String, Book>? = reservationQueue.removeFirstOrNull()
    fun queueSize(): Int = reservationQueue.size

    fun topByLoans(n: Int): List<Book> =
        books.values.sortedByDescending { it.totalLoans }.take(n)

    fun topThickest(n: Int): List<PrintedBook> =
        books.values.filterIsInstance<PrintedBook>()
            .sortedByDescending { it.pages }
            .take(n)

    fun topAuthors(n: Int): List<Pair<String, Int>> =
        books.values.groupBy { it.author }
            .map { (author, list) -> author to list.size }
            .sortedByDescending { it.second }
            .take(n)

    fun totalCatalogValue(): Double =
        books.values.sumOf { it.price.amount * it.copiesInStock }

    fun totalCopies(): Int = books.values.sumOf { it.copiesInStock }

    fun averagePrice(): Double =
        if (books.isEmpty()) 0.0
        else books.values.map { it.price.amount }.average()

    fun hasAvailable(): Boolean = books.values.any { it.isAvailable }
    fun unavailableCount(): Int = books.values.count { !it.isAvailable }

    fun addBookByAuthorYear(book: Book) {
        byAuthorYear[book.author to book.year] = book
    }

    fun findByIsbn(isbn: String): Book? = byIsbn[isbn]
    fun hasIsbn(isbn: String): Boolean = isbn in byIsbn
    fun removeBook(book: Book): Boolean {
        val isbn = book.isbn ?: return false
        return books.remove(isbn) != null
    }

    val size: Int get() = books.size
    fun all(): List<Book> = books.values.toList()
    override fun toString(): String = "Библиотека «$name» ($size книг)"

    private val genreIndex: Map<Genre, List<Book>> by lazy {
        books.values.groupBy { it.genre }
    }

    fun byGenre(): Map<Genre, List<Book>> = genreIndex

    fun countByGenre(): Map<Genre, Int> = books.values.groupBy { it.genre }.mapValues { (_, list) -> list.size }

    fun place(book: Book, row: Int, col: Int): Boolean {
        if (row !in shelves.indices || col !in shelves[row].indices) {
            throw IllegalArgumentException("Некорректные координаты полки")
        }

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

    private val genreCountDelegate = Memoized {
        println("[Memo] Computing genre counts...")
        books.values.groupingBy { it.genre }.eachCount()
    }
    val genreCount: Map<Genre, Int> by genreCountDelegate


    fun addBookWithDsl(book: Book) {
        book.isbn?.let { isbn ->
            books[isbn] = book
        }

        genreCountDelegate.reset()
    }

    fun allBooks(): List<Book> = books.values.toList()
    fun filter(filter: BookFilter): List<Book> = allBooks().filter { filter(it) }

    private val bookGenreIndex: Map<Genre, List<Book>> by lazy {
        books.values.groupBy { it.genre }
    }

}