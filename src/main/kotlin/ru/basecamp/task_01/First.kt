package ru.basecamp.task_01

private val CLEAN_REGEX = Regex("[\\s-]")
private val DIGITS_REGEX = Regex("\\d+")

fun main() {
    val bookData = readBookData()

    val title = bookData[0]
    val author = bookData[1]
    val year = bookData[2]
    val pages = bookData[3]
    val price = bookData[4]
    val copiesInStock = bookData[5]

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

    checkIsbn()

    runLoans()
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

fun readBookData(): List<String?> {
    val bookData = mutableListOf<String?>()
    print("Введите название книги: ")
    val title: String = readln()
    print("Введите автора: ")
    val author: String = readln()
    print("Введите год издания: ")
    val year: Int = readln().toInt()
    print("Введите количество страниц: ")
    val pages: Int = readln().toInt()
    print("Введите цену (руб.): ")
    val price: Double = readln().toDouble()
    print("Введите количество экземпляров: ")
    val quantity: Int = readln().toInt()

    print("Введите язык оригинала: ")
    val originalLanguage: String? = readln().ifBlank { null }
    print("Введите ФИО переводчика: ")
    val translator: String? = readln().ifBlank { null }
    print("Введите номер издания: ")
    val edition: Int? = readln().toIntOrNull()
    print("Введите ISBN: ")
    val cleanedIsbn = cleanIsbn(readln())
    val isbn: String? = if (isValidIsbn(cleanedIsbn)) cleanedIsbn else null

    bookData.add(originalLanguage)
    bookData.add(translator)
    bookData.add(edition?.toString())
    bookData.add(isbn)

    bookData.add(title)
    bookData.add(author)
    bookData.add(year.toString())
    bookData.add(pages.toString())
    bookData.add(price.toString())
    bookData.add(quantity.toString())

    return bookData
}

fun formatPrice(price: String?): String = "%.2f руб.".format(price)


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
    title: String?,
    year: String?,
    pages: String?,
    price: String?,
    copiesInStock: String?
): List<String> {
    val errorsList = mutableListOf<String>()

    val yearInt = year?.toIntOrNull()
    if (yearInt == null) {
        errorsList.add("Год не указан или имеет неверный формат: $year")
    } else if (yearInt !in 1450..2026) {
        errorsList.add("Год $yearInt вне диапазона 1450-2026")
    }

    val pagesInt = pages?.toIntOrNull()
    if (pagesInt == null) {
        errorsList.add("Количество страниц не указано или имеет неверный формат: $pages")
    } else if (pagesInt !in 1..10_000) {
        errorsList.add("Количество страниц $pagesInt вне диапазона 1-10000")
    }

    val priceDouble = price?.toDoubleOrNull()
    if (priceDouble == null) {
        errorsList.add("Цена не указана или имеет неверный формат: $price")
    } else if (priceDouble < 0) {
        errorsList.add("Цена $priceDouble не может быть отрицательной")
    }

    val copiesInt = copiesInStock?.toIntOrNull()
    if (copiesInt == null) {
        errorsList.add("Количество экземпляров не указано или имеет неверный формат: $copiesInStock")
    } else if (copiesInt < 0) {
        errorsList.add("Количество экземпляров $copiesInt не может быть отрицательным")
    }

    if (title.isNullOrBlank()) {
        errorsList.add("Название книги не может быть пустым или состоять из пробелов")
    }

    return errorsList
}

fun printBookCard(
    title: String?,
    author: String?,
    year: String?,
    pages: String?,
    price: String?,
    quantity: String?
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

fun cleanIsbn(rawIsbn: String?): String {
    val cleanIsbn = rawIsbn?.replace(CLEAN_REGEX, "")

    if (cleanIsbn?.length != 13 || !cleanIsbn.matches(DIGITS_REGEX)) {
        return "ISBN должен быть равен 13-и символам и содержать только цифры"
    }

    var result = 0
    for (i in cleanIsbn.indices) {
        result += if (i % 2 == 0) {
            cleanIsbn[i].digitToInt() * 1
        }
        else {
            cleanIsbn[i].digitToInt() * 3
        }
    }

    if (result % 10 == 0) println("ISBN валиден, контрольная сумма равна $result")
    else println("ISBN не валиден, контрольная сумма равна $result, попробуйте ещё раз")

    return cleanIsbn
}