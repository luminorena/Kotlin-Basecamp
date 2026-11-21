package ru.basecamp.task_04

fun main() {
    println(parseYear(2008))
    println(parseYear("2008"))
    println(parseYear("две тысячи восьмой"))
    println(parseYear(null))
    println(parseYear(2.5))
}

fun parseYear(value: Any?): Int? {
    return (value as? Int) ?: (value as? String)?.toIntOrNull()
}