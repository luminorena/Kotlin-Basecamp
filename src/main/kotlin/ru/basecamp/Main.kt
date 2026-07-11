package ru.basecamp

import ru.basecamp.library.io.Library
import ru.basecamp.library.io.PrintedBook
import ru.basecamp.library.model.SimpleBook
import java.nio.file.Paths
import kotlin.io.path.Path

fun main() {
    val lib = Library("Original")
    val cleanCode = SimpleBook(
        title = "Чистый код",
        author = "Р. Мартин",
        year = 2008,
        pages = 464,
        price = 1290.0,
        initialCopies = 0,
        isbn = "9785916719892"
    )

    val book = SimpleBook(
        title = "Test",
        author = "T. Test",
        year = 2008,
        pages = 464,
        price = 190.0,
        initialCopies = 0,
        isbn = "7685916719882"
    )

    listOf(cleanCode, book)
    lib.saveToTsv(Path("library.tsv"))
    println("Сохранено в ${Path("library.tsv").toAbsolutePath()}")

    val original = Library("Original").apply {
        addBook(
            PrintedBook(
                "Чистый код", "Р. Мартин", 2008, 454, 1290.0,
                3,
                "9785916719892"
            )
        )
        addBook(
            PrintedBook(
                "Война и мир", "Л. Толстой", 1869, 300, 750.0,
                2,
                isbn = "9785170123469"
            )
        )
    }
    val tsvPath = Path("library.tsv")
    original.saveToTsv(tsvPath)

    val loaded = lib.loadLibraryFromTsv("Loaded", tsvPath)
    println("Оригинал: ${original.size} книг, после загрузки: ${loaded.size}")
    loaded.getBooks().forEach { println(" - ${it.title} (${it.year})") }

    println("Книг Толстого: ${lib.countLinesContaining(tsvPath, "Толстой")}")

    val filePath = Paths.get("library.tsv")
    println(filePath)
    lib.saveWithBackup(filePath)

    lib.listBackups(Path("backups"))

    lib.exportZip(Path("library.zip"))
    lib.listZipContents(Path("library.zip"))
}