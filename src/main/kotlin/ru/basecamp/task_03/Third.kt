package ru.basecamp.task_03

import ru.basecamp.task_02.printBookCard

fun main() {
    printBookCard(
        title = "Война и мир",
        author = "Лев Толстой",
        year = 1869,
        pages = 1225,
        price = 799.90,
        quantity = 3,
        isbn = 1212345678743L,
        withFancyFrame = true
    )
}

fun digitsSum(n: Long): Int =
    if (n < 10) n.toInt()
    else (n % 10).toInt() + digitsSum(n / 10)

