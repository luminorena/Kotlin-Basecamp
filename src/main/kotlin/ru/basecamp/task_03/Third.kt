package ru.basecamp.task_03

/**
 * Задача 3. Рекурсия — сумма цифр ISBN
 * Добавьте функцию, которая считает сумму цифр числового ISBN рекурсивно:
 * База рекурсии: n < 10 - вернуть n.toInt(). Шаг: (n % 10).toInt() + digitsSum(n / 10).
 * После реализации добавьте в карточку строку "Сумма цифр ISBN: <число>".
 */

fun main() {
    println("Сумма цифр ISBN: " + digitsSum(1212345678743))
}

fun digitsSum(n: Long): Int = if (n < 10) n.toInt() else (n % 10).toInt() + digitsSum(n / 10)


