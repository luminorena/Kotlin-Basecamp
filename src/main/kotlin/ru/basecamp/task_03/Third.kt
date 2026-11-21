package ru.basecamp.task_03

fun main() {
    println(describeIsbn(null))
    println(describeIsbn("9785916719892"))
}

fun describeIsbn(isbn: String?): String {
    if (isbn == null) return "ISBN отсутствует"
    return "Длина: ${isbn.length}, GS1 префикс ${isbn.substring(0, 3)}"
}