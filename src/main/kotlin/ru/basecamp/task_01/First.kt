package ru.basecamp.task_01

private val CLEAN_REGEX = Regex("[\\s-]")
private val DIGITS_REGEX = Regex("\\d+")

private const val VALIDATE_ERROR = "Число страниц должно быть больше нуля"
private const val BROCHURE = "Брошюра"
private const val STANDARD_BOOK = "Стандартная книга"
private const val THICK_BOOK = "Толстая книга"
private const val VERY_THICK_BOOK = "Очень толстая книга"
private const val ENORMOUS_BOOK = "Кирпич"

fun main() {
    val bookData = readBookData()

    val title = bookData[0]
    val author = bookData[1]
    val year = bookData[2].toInt()
    val pages = bookData[3].toInt()
    val price = bookData[4].toDouble()
    val copiesInStock = bookData[5].toInt()

    val errors = validateBook(
        title = title,
        year = year,
        pages = pages,
        price = price,
        copiesInStock = copiesInStock
    )

    if (errors.isEmpty()) {
        printBookCard(
            title = title,
            author = author,
            year = year,
            pages = pages,
            price = price,
            quantity = copiesInStock
        )
    } else {
        for (error in errors) {
            println(error)
        }
    }

    printBookInfo(
        title = title,
        author = author,
        pages = pages,
        price = price
    )

    checkIsbn()

    runLoans()
}

fun printBookInfo(
    title: String,
    author: String,
    pages: Int,
    price: Double
) {
    println("Категория по количеству страниц: ${categorizePages(pages)}")
    println("Короткое название: ${makeShortTitle(title)}")
    println("Инициалы автора: ${makeAuthorInitials(author)}")
    println("Цена в формате: ${formatPrice(price)}")
}

fun checkIsbn() {
    print("Введите ISBN: ")
    val rawIsbn = readln()

    val isbn = cleanIsbn(rawIsbn)

    if (isValidIsbn(isbn)) {
        println("ISBN валиден: $isbn")
    } else {
        println(
            "ISBN не валиден: $isbn. " +
                    "ISBN должен быть равен 13-и символам, содержать только цифры " +
                    "и иметь корректную контрольную сумму."
        )
    }
}

fun runLoans() {
    print("Сколько экземпляров книги есть в библиотеке? ")
    val copies = readln().toInt()

    print("Сколько читателей хотят взять книгу? ")
    val requestedLoans = readln().toInt()

    val loansResult = simulateLoans(copies, requestedLoans)
    println(loansResult)
}

fun readBookData(): List<String> {
    val bookData = mutableListOf<String>()
    print("Введите название книги: ")
    val title = readln()
    print("Введите автора: ")
    val author = readln()
    print("Введите год издания: ")
    val year: Int? = readln().toIntOrNull()
    print("Введите количество страниц: ")
    val pages: Int? = readln().toIntOrNull()
    print("Введите цену (руб.): ")
    val price: Double = readln().toDouble()
    print("Введите количество экземпляров: ")
    val quantity: Int? = readln().toIntOrNull()

    bookData.add(title)
    bookData.add(author)
    bookData.add(year.toString())
    bookData.add(pages.toString())
    bookData.add(price.toString())
    bookData.add(quantity.toString())

    return bookData
}

fun cleanIsbn(rawIsbn: String): String {
    return rawIsbn.replace(CLEAN_REGEX, "")
}

fun isValidIsbn(isbn: String): Boolean {
    if (isbn.length != 13 || !isbn.matches(DIGITS_REGEX)) {
        return false
    }

    var result = 0
    for (i in isbn.indices) {
        result += isbn[i].digitToInt() * if (i % 2 == 0) 1 else 3
    }

    return result % 10 == 0
}


fun validateBook(
    title: String,
    year: Int,
    pages: Int,
    price: Double,
    copiesInStock: Int
): List<String> {
    val errorsList = mutableListOf<String>()

    if (year !in 1450..2026) {
        errorsList.add("Год $year вне диапазона 1450-2026")
    }

    if (pages !in 1..10_000) {
        errorsList.add("Количество страниц $pages вне диапазона 1-10000")
    }

    if (price < 0) {
        errorsList.add("Цена $price не может быть отрицательной")
    }

    if (copiesInStock < 0) {
        errorsList.add("Количество экземпляров $copiesInStock не может быть отрицательным")
    }

    if (title.isBlank()) {
        errorsList.add("Название книги не может быть пустым или состоять из пробелов")
    }

    return errorsList
}

fun categorizePages(pages: Int): String = when {
    pages <= 0 -> VALIDATE_ERROR
    pages < 50 -> BROCHURE
    pages in 50..199 -> STANDARD_BOOK
    pages in 200..499 -> THICK_BOOK
    pages in 500..999 -> VERY_THICK_BOOK
    else -> ENORMOUS_BOOK
}

fun makeShortTitle(title: String, maxLen: Int = 30): String =
    if (title.length > maxLen) {
        title.take(maxLen - 3) + "..."
    } else {
        title
    }

fun formatPrice(price: Double): String = "%.2f руб.".format(price)

fun simulateLoans(copies: Int, requestedLoans: Int): String {
    var totalLoans = 0
    var currentlyOnHand = 0

    val log = mutableListOf<String>()

    for (i in 1..requestedLoans) {
        if (currentlyOnHand < copies) {
            currentlyOnHand++
            totalLoans++
            log.add("Выдача $i: на руках $currentlyOnHand из $copies")
        } else {
            log.add("Выдача $i: отказ — все экземпляры заняты")
            break
        }
    }

    log.add("Итого выдано: $totalLoans")
    log.add("Свободно осталось: ${copies - currentlyOnHand}")

    return log.joinToString("\n")
}

fun makeAuthorInitials(fullName: String): String {
    val words = fullName.split(" ")

    return words
        .mapIndexed { index, word ->
            if (index == words.lastIndex) {
                word
            } else {
                word.take(1).uppercase() + "."
            }
        }
        .joinToString(" ")
}

fun printBookCard(
    title: String,
    author: String,
    year: Int,
    pages: Int,
    price: Double,
    quantity: Int
) {
    println(
        """
        Карточка книги:
        Название: $title,
        Автор: $author,
        Год: $year,
        Количество страниц: $pages,
        Цена: ${formatPrice(price)},
        Количество: $quantity
        """.trimIndent()
    )
}