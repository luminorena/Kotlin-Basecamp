package ru.basecamp.task_04

/**
 * Используя while, реализуйте «угадайку» года издания книги:
 * Угадайте год издания книги.
 * Введите число (или 0 для выхода): > 2000 Слишком рано > 2010
 * Слишком рано > 2008 Угадали! Попыток: 3
 * Год — взять из переменной year. Цикл крутится,
 * пока не угадают или не введут 0.
 */
fun main() {

    val secretYear: Int = 2020
    var attempts: Int = 0

    while(true) {
        println("Введите положительное число года издания книги (или 0 для выхода):")
        val year: Int = readln().toInt()
        when {
            year == 0 -> {
                println("Выход из программы")
                break
            }
            year > secretYear -> {
                println("Слишком большой")
                attempts++
            }
            year < secretYear -> {
                println("Слишком маленький")
                attempts++
            }
            year == secretYear -> {
                println("Бинго! Вы угадали!, Количество попыток: $attempts")
                break
            }
        }
    }
}