package ru.basecamp.task_02

import java.util.Locale.getDefault

/**
 * Задача 2. Обработка названия
 * Добавьте логику обработки title:
 * 1. Короткая версия для списков: если title.length > 30 — выведите первые 27 символов + "...". Иначе — само
 * название.
 * val shortTitle = if (title.length > 30) title.take(27) + "..." else title
 * 2. Инициалы автора: из author (например, "Роберт Сесил Мартин") сделайте "Р. С. Мартин". Используйте
 * split(" ") и работу с подстроками (или с Char).
 * 3. Проверка на «всё капсом»: если title равен title.uppercase() (т.е. написан полностью капсом) — выведите
 * предупреждение ”Название всё капсом, переведём в Title Case" и приведите к виду "Чистый Код" (первая
 * буква каждого слова — заглавная). Подсказка: replaceFirstChar { it.uppercase() } на каждом слове.
 */
fun main() {
    print("Введите название книги: ")
    val title: String = readln()

    val shortTitle = if (title.length > 30) title.take(27) + "..." else title
    println(shortTitle)

    print("Введите автора: ")
    val author: String = readln()

    val split = author.split(" ")
    for (i in split.indices) {
        val word = split[i]
        val res = if (i == split.lastIndex) word
        else word.take(1).uppercase(getDefault()) + ". "
        print(res)
    }

    if (title == title.uppercase()) {
        println("Название всё капсом, переведём в Title Case")
        val split = title.split(" ")
        for (i in split.indices) {
            val replaceFirstChar = split[i].lowercase().replaceFirstChar { it.uppercase() }
            print("$replaceFirstChar ")
        }
    }
}