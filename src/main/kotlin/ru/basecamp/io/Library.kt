package ru.basecamp.io

class Library {
    data class Book(
        val title: String,
        val author: String,
        val year: Int
    )

    fun all(): List<Book> = books

    val books = listOf(
        Book("Котлин в действии", "Дмитрий Жемеров", 2025),
        Book("Герберт Шидлт", "Java. Полное руководство", 2024),
        Book("Чистый код", "Роберт Мартин", 2022)
    )

     fun extractIsbns(text: String): List<String> {
        val pattern = Regex("""(?:97[89])(?:[-\s]?\d){10}""")
        return pattern.findAll(text)
            .map { it.value.replace(Regex("[-\\s]"), "") }
            .toList()
    }

    fun searchHighlighted(query: String): List<String> {
        val regex = Regex(Regex.escape(query), RegexOption.IGNORE_CASE)
        return books
            .filter { regex.containsMatchIn(it.title) || regex.containsMatchIn(it.author) }
            .map { book ->
                val highlightedTitle = regex.replace(book.title) { match -> "[${match.value}]" }
                val highlightedAuthor = regex.replace(book.author) { match -> "[${match.value}]" }
                "$highlightedTitle — $highlightedAuthor"
            }
    }

    fun extractLastName(author: String): String {
        Regex("""^([^,]+),""").find(author)?.let { return it.groupValues[1].trim() }
        val tokens = author.trim().split(Regex("\\s+"))
        return tokens.last { !it.matches(Regex("""[A-ZА-ЯЁ]\.?""")) }
    }

    fun report(name: String, size: Int): String = buildString {
        appendLine("╔═══════════════════════════════════════╗")
        appendLine("║ Каталог: ${name.padEnd(28)} ║")
        appendLine("╠═══════════════════════════════════════╣")
        for (book in all().sortedBy { it.title }) {
            appendLine("║ ${book.title.take(30).padEnd(30)} ${"%5d".format(book.year)} ║")
        }
        appendLine("╠═══════════════════════════════════════╣")
        appendLine("║ Всего книг: ${size.toString().padEnd(26)} ║")
        appendLine("╚═══════════════════════════════════════╝")
    }

    fun parseMarc(text: String): Map<String, Map<String, String>> {
        val fieldRegex = Regex("""^(\d{3})\s+\S*\$(.+)$""", RegexOption.MULTILINE)
        val subfieldRegex = Regex("""\$(\w)([^$]+)""")
        return fieldRegex.findAll(text).associate { fieldMatch ->
            val tag = fieldMatch.groupValues[1]
            val rest = fieldMatch.groupValues[2]
            val subfields = subfieldRegex.findAll("\$$rest")
                .associate { it.groupValues[1] to it.groupValues[2].trim() }
            tag to subfields
        }
    }
}