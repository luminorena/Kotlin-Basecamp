package ru.basecamp.tests

import Library
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import ru.basecamp.exception.BookAlreadyExistsException
import ru.basecamp.exception.BookNotFoundException
import ru.basecamp.model.PrintedBook
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class LibraryTest {

    private lateinit var library: Library
    private lateinit var sampleBook: PrintedBook

    @BeforeEach
    fun setup() {
        library = Library("Test Library")
        sampleBook = PrintedBook(
            "Чистый код", "Р. Мартин", 2008, 464, 2300.00,
            isbn = "9785916719892", initialCopies = 20
        )
    }

    @Test
    @DisplayName("Новая библиотека пустая")
    fun emptyLibrary() {
        assertEquals(0, library.size)
        assertNull(library.findByIsbn("9785916719892"))
    }

    @Test
    @DisplayName("Добавление книги увеличивает size и индекс по ISBN")
    fun addBook() {
        library.addBook(sampleBook)

        val found = library.findByIsbn("9785916719892")
        assertNotNull(found)
        assertEquals(sampleBook.title, found.title)
        assertEquals(sampleBook.author, found.author)
        assertEquals(sampleBook.year, found.year)
        assertEquals(sampleBook.isbn, found.isbn)
    }

    @Test
    @DisplayName("Дубликат по ISBN бросает исключение")
    fun duplicateIsbnThrows() {
        library.addBook(sampleBook)
        assertThrows<BookAlreadyExistsException> {
            library.addBook(sampleBook)
        }
    }

    @Test
    @DisplayName("getByIsbn бросает, если книги нет")
    fun getByIsbnThrows() {
        val ex = assertThrows<BookNotFoundException> {
            library.getByIsbn("0000000000000")
        }
        assertEquals("0000000000000", ex.isbn)
    }

    @Test
    @DisplayName("Поиск по предикату возвращает корректные книги")
    fun searchPredicate() {
        library.addBook(sampleBook)
        val results = library.search { it.year > 2000 }
        assertEquals(1, results.size)
    }
}