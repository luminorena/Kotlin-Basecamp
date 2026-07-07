package ru.basecamp.library.helpers

fun safelyParseYear(input: String): Int? = try {
    input.toInt().also {
        if (it !in 1450..2100) throw IllegalArgumentException("Год $it вне диапазона")
    }
} catch (e: NumberFormatException) {
    println("Не число: $input")
    null
} catch (e: IllegalArgumentException) {
    println("Логическая ошибка: ${e.message}")
    null
}

fun parseYearResult(input: String): Result<Int> = runCatching {
    val n = input.toInt()
    require(n in 1450..2100) { "Год $n вне диапазона" }
    n
}

