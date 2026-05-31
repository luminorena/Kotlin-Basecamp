package ru.otus.ru.otus.task_02

/**
 *        Задача 2. Чтение книги с консоли.
 *        Добавьте перед выводом карточки диалог с пользователем:
 *          Введите название книги: <ввод>
 *          Введите автора: <ввод>
 *          Введите год издания: <ввод>
 *          Введите количество страниц: <ввод>
 *          Введите цену (руб.): <ввод>
 *          Введите количество экземпляров: <ввод>
 *          После ввода — печатается та же карточка, но уже с введёнными данными.
 *          Требования:
 *           - Используйте readln() (а не Scanner).
 *           - Числовые поля приводите к нужному типу:
 *            readln().toInt() / readln().toDouble().
 *          - Все переменные — val (читаем один раз, потом не меняем).
 */
fun main() {
    print("Введите название книги: ")
    val bookName: String = readln()
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

    println("Название книги: $bookName")
    println("Автор: $author")
    println("Год издания: $year")
    println("Количество страниц: $pages")
    println("Цена: $price")
    println("Количество экземпляров: $quantity")
}