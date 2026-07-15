package ru.basecamp

import ru.basecamp.io.Library
import ru.basecamp.model.LibraryUser

fun main() {
    val library = Library()
    val text = """
        "Серия: Бестселлеры O'Reilly. ISBN: 978-5-91671-989-2. Страниц: 464."
        "isbn 9785916719892, мягкая обложка"
        "Артикул 978-5-9907763-1-3 (13 цифр)"
        "Книга без ISBN — самиздат"
    """.trimIndent()
    val result = library.extractIsbns(text)
    println(result)

    LibraryUser("Анна", "ann@example.com")
    try {
        LibraryUser("Боб", "not-an-email")
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }

    println(library.searchHighlighted("Java"))

    println(library.extractLastName("Пушкин А.С."))

    println(library.report("programmer", 2))

    println(
        library.parseMarc(
            """
            245 10${'$'}aЧистый код${'$'}bруководство для разработчиков${'$'}cРоберт Мартин
            260 ${'$'}aМосква${'$'}bПитер${'$'}c2008
            020 ${'$'}a978-5-91671-989-2
        """.trimIndent()
        )
    )
}