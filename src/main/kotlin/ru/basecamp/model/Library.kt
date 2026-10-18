package ru.basecamp.model

import ru.basecamp.io.LoanResult
import ru.basecamp.util.Memoized
import java.lang.reflect.Member

class Library(val name: String, rows: Int = 5, cols: Int = 10) {
    val books = mutableListOf<Book>()
    private val loans = mutableMapOf<Book, Member>()
    private val byIsbn = mutableMapOf<String, Book>()
    private val shelves: Array<Array<String?>> = Array(rows) { arrayOfNulls(cols) }

    private val byAuthorYear: MutableMap<Pair<String, Int>, Book> = mutableMapOf()
    lateinit var defaultLoanPolicy: LoanPolicy

    fun lend(book: Book, member: Member): LoanResult {
        check(::defaultLoanPolicy.isInitialized) {
            "Loan policy not configured"
        }

        val libraryBook = books.find { it.isbn == book.isbn }
            ?: return LoanResult.BookNotInLibrary(book.isbn)

        val loanedCopies = loans.keys.count { it.isbn == book.isbn }
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
        books.add(book)
        book.isbn?.let { isbn ->
            require(isbn !in byIsbn) { "Книга с ISBN $isbn уже есть в каталоге" }
            byIsbn[isbn] = book
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
        books.sortedByDescending { it.totalLoans }.take(n)

    fun topThickest(n: Int): List<PrintedBook> =
        books.filterIsInstance<PrintedBook>()
            .sortedByDescending { it.pages }
            .take(n)

    fun topAuthors(n: Int): List<Pair<String, Int>> =
        books.groupBy { it.author }
            .map { (author, list) -> author to list.size }
            .sortedByDescending { it.second }
            .take(n)

    fun totalCatalogValue(): Double =
        books.sumOf { it.price.amount * it.copiesInStock }

    fun totalCopies(): Int = books.sumOf { it.copiesInStock }

    fun averagePrice(): Double =
        if (books.isEmpty()) 0.0
        else books.map { it.price.amount }.average()

    fun hasAvailable(): Boolean = books.any { it.isAvailable }
    fun unavailableCount(): Int = books.count { !it.isAvailable }

    fun addBookByAuthorYear(book: Book){
        byAuthorYear[book.author to book.year] = book
    }
    fun findByIsbn(isbn: String): Book? = byIsbn[isbn]
    fun hasIsbn(isbn: String): Boolean = isbn in byIsbn
    fun removeBook(book: Book): Boolean = books.remove(book)
    val size: Int get() = books.size
    fun all(): List<Book> = books.toList()
    override fun toString(): String = "Библиотека «$name» ($size книг)"

    private val genreIndex: Map<Genre, List<Book>> by lazy {
        books.groupBy { it.genre }
    }

    fun byGenre(): Map<Genre, List<Book>> = genreIndex

    fun countByGenre(): Map<Genre, Int> = books.groupBy { it.genre }.mapValues { (_, list) -> list.size }

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

    private val genreCountDelegate = Memoized {
        println("[Memo] Computing genre counts...")
        books.groupingBy { it.genre }.eachCount()
    }
    val genreCount: Map<Genre, Int> by genreCountDelegate
    fun addBookWithDsl(book: Book) {
        books.add(book)
        genreCountDelegate.reset()
    }
}
