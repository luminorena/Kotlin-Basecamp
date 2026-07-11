package ru.basecamp.library.io

import ru.basecamp.library.error.CatalogCorruptedException
import ru.basecamp.library.model.SimpleBook
import java.io.EOFException
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.*

operator fun <T> List<T>.component6(): T = this[5]
operator fun <T> List<T>.component7(): T = this[6]
operator fun <T> List<T>.component8(): T = this[7]

open class Library(name: String?) {

    private val libraryName = name

    private val books = mutableListOf(
        SimpleBook(
            title = "Kotlin in Action",
            author = "Dmitry Jemerov",
            year = 2017,
            pages = 360,
            price = 45.0,
            initialCopies = 3,
            isbn = "9785916719765"
        ),
        SimpleBook(
            title = "Effective Java",
            author = "Joshua Bloch",
            year = 2018,
            pages = 416,
            price = 55.0,
            initialCopies = 2,
            isbn = "9785916719892"
        )
    )

    val size: Int
        get() = books.size

    fun getBooks(): List<SimpleBook> = books

    fun countLinesContaining(path: Path, substring: String): Int =
        path.useLines { lines ->
            lines.count { it.contains(substring, ignoreCase = true) }
        }

    fun saveWithBackup(path: Path) {
        val backupDir = path.parent?.resolve("backups") ?: Path.of("backups")
        backupDir.createDirectories()

        if (path.exists()) {
            val timestamp = System.currentTimeMillis()
            val backupPath = backupDir.resolve("${path.nameWithoutExtension}-$timestamp.tsv")
            path.copyTo(backupPath, overwrite = false)
            println("Backup: $backupPath")
        }

        saveToTsv(path)
    }

    fun byIsbn(isbn: String?): SimpleBook? {
        val normalizedIsbn = isbn?.trim()
            ?: throw IllegalArgumentException("isbn is null")

        return books.firstOrNull { it.isbn == normalizedIsbn }
    }

    fun addBook(book: SimpleBook) {
        val isbn = book.isbn ?: return
        byIsbn(isbn)
    }

    fun saveToTsv(path: Path) {
        val header = listOf("type", "title", "author", "year", "pages", "price", "copies", "isbn")
        val rows = books.map { book ->
            listOf(
                book::class.simpleName ?: "Book",
                book.title,
                book.author,
                book.year.toString(),
                book.pages.toString(),
                book.price.toString(),
                book.copiesInStock.toString(),
                book.isbn ?: "",
            ).joinToString("\t")
        }
        path.writeLines(listOf(header.joinToString("\t")) + rows)
    }

    fun loadLibraryFromTsv(name: String, path: Path): Library {
        if (!path.exists()) {
            throw CatalogCorruptedException(
                "Файл $path не найден",
                FileNotFoundException(path.toString())
            )
        }

        val lines = path.readLines()
        if (lines.isEmpty()) {
            throw CatalogCorruptedException("Файл пуст", EOFException())
        }

        val library = Library(name)

        for ((lineNo, line) in lines.withIndex()) {
            if (lineNo == 0) continue

            try {
                val cols = line.split("\t")
                require(cols.size == 8) { "Ожидалось 8 колонок, получено ${cols.size}" }

                val (type, title, author, yearStr, pagesStr, priceStr, initialCopiesStr, isbn) = cols

                when (type) {
                    "SimpleBook" -> {
                        val book = SimpleBook(
                            title = title,
                            author = author,
                            year = yearStr.toInt(),
                            pages = pagesStr.toInt(),
                            price = priceStr.toDouble(),
                            initialCopies = initialCopiesStr.toInt(),
                            isbn = isbn
                        )
                        library.addBook(book)
                    }


                    "PrintedBook" -> {
                        val book = PrintedBook(
                            title = title,
                            author = author,
                            year = yearStr.toInt(),
                            pages = pagesStr.toInt(),
                            price = priceStr.toDouble(),
                            initialCopies = initialCopiesStr.toInt(),
                            isbn = isbn
                        )
                        library.addBook(book)
                    }

                    "Ebook" -> {
                        val ebook = EBook(
                            title = title,
                            author = author,
                            year = yearStr.toInt(),
                            pages = pagesStr.toInt(),
                            price = priceStr.toDouble(),
                            isbn = isbn,
                            sizeMb = 0.0,
                            format = ""
                        )
                        library.addBook(ebook)
                    }

                    "AudioBook" -> {
                        val audioBook = AudioBook(
                            title = title,
                            author = author,
                            year = yearStr.toInt(),
                            pages = pagesStr.toInt(),
                            price = priceStr.toDouble(),
                            isbn = isbn,
                            initialCopies = initialCopiesStr.toInt(),
                            durationMinutes = 0,
                            narrator = ""
                        )
                        library.addBook(audioBook)
                    }

                    else -> throw IllegalArgumentException("Неизвестный тип: $type")
                }
            } catch (e: Exception) {
                throw CatalogCorruptedException("Ошибка в строке ${lineNo + 1}: «$line»", e)
            }
        }

        return library
    }

    fun listBackups(dir: Path) {
        if (!dir.exists()) {
            println("Нет backup'ов")
            return
        }
        dir.toFile().walkTopDown()
            .filter { it.isFile && it.extension == "tsv" }
            .sortedByDescending { it.lastModified() }
            .forEach { f -> println("${f.name}\t${f.length()} bytes") }
    }

    fun exportZip(zipPath: Path) {
        ZipOutputStream(FileOutputStream(zipPath.toFile())).use { zip ->
            zip.putNextEntry(ZipEntry("README.txt"))
            zip.write("Каталог: $libraryName\nВсего книг: $size\n".toByteArray())
            zip.closeEntry()

            zip.putNextEntry(ZipEntry("library.tsv"))
            val tempTsv = Files.createTempFile("library", ".tsv")
            saveToTsv(tempTsv)
            zip.write(tempTsv.readBytes())
            zip.closeEntry()
            tempTsv.deleteIfExists()
        }
        println("Архив: ${zipPath.toAbsolutePath()}")
    }

    fun listZipContents(zipPath: Path) {
        ZipFile(zipPath.toFile()).use { zip ->
            for (entry in zip.entries()) {
                println("${entry.name}\t${entry.size} bytes")
            }
        }
    }
}