package ru.basecamp.task_04

import java.math.BigInteger

fun main() {
    val hugeNumber = "9".repeat(100000).toBigInteger()

    println("Запуск с tailrec:")
    println(digitsSumTail(hugeNumber))

    println("Запуск без tailrec:")
    println(digitsSumNoTailrec(hugeNumber))
}

tailrec fun digitsSumTail(
    n: BigInteger,
    acc: Int = 0
): Int {
    val ten = BigInteger.TEN

    return if (n < ten) {
        acc + n.toInt()
    } else {
        digitsSumTail(
            n = n / ten,
            acc = acc + (n % ten).toInt()
        )
    }
}

fun digitsSumNoTailrec(
    n: BigInteger,
    acc: Int = 0
): Int {
    val ten = BigInteger.TEN

    return if (n < ten) {
        acc + n.toInt()
    } else {
        digitsSumNoTailrec(
            n = n / ten,
            acc = acc + (n % ten).toInt()
        )
    }
}