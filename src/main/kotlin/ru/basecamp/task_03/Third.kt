package ru.basecamp.task_03

/**
 * Задача 3. Smart cast в действии
 * Добавьте функцию:
 * Пример:
 * q describeIsbn(null) - "ISBN отсутствует"
 * q describeIsbn("9785916719892") - "Длина 13, GS1 префикс 978"
 * Внутри тела:
 * Обратите внимание: после if (isbn == null) return ... smart cast превращает isbn: String? в String — и .substring
 * работает без ?.
 * fun describeIsbn(isbn: String?): String {
 * // если null — вернуть "ISBN отсутствует"
 * // если не null — длина и первые 3 цифры (префикс — "GS1 prefix")
 * // НЕ использовать !!, использовать smart cast после проверки
 * Обратите внимание: после if (isbn == null) return ... smart cast превращает isbn: String? в String — и .substring
 * работает без ?.
 */

fun main() {
    println(describeIsbn(null))
    println(describeIsbn("9785916719892"))
}

fun describeIsbn(isbn: String?): String {
    return isbn?.let { "Длина: ${isbn.length}, GS1 префикс ${isbn.take(3)}" } ?: "ISBN отсутствует"
}