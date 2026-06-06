package ru.basecamp.task_04

/**
 * Конвертация года из строки
 * В файле уже есть: val year: Int = readln().toInt()
 * Что будет, если пользователь ввёл "2008 год"? Падение.
 * Безопасный вариант —
 * readln().toIntOrNull(), который возвращает Int? (nullable).
 * Перепишите чтение года:
 * Что такое ?: (Elvis) и error(...) — мы пока не разбирали, но идея понятна.
 * val yearInput = readln()
 * val year = yearInput.toIntOrNull()
 * ?: error("Год должен быть числом, а не «$yearInput»")
 * val year: Int
 */
fun main() {
    println("Введите год издания")
    val yearInput = readln()
    val year = yearInput.toIntOrNull()
        ?: error("Год должен быть числом, а не «$yearInput»")
    println(year)
}