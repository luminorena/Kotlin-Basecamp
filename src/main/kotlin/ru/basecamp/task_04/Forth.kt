package ru.basecamp.task_04

import java.math.BigInteger

/**
 * Перепишите digitsSum в хвостово-рекурсивную форму:
 * Уберите tailrec и попробуйте посчитать сумму цифр очень длинного числа (миллионы цифр — можно
 * сгенерировать). С tailrec — отработает мгновенно, без — упадёт с StackOverflowError.
 * Поэкспериментируйте, сделайте короткий вывод.
 */

fun main() {
    val hugeNumber = "9".repeat(100000).toBigInteger()
    println(digitsSumTail(hugeNumber))
}

 tailrec fun digitsSumTail(n: BigInteger, acc: Int = 0): Int {
     val ten = BigInteger.TEN
     return if (n < ten) acc + n.toInt()
     else digitsSumTail(n / ten, acc + (n % ten).toInt())
 }


/*
Чтобы сработало переполнение стека, надо переписать на BigInteger, с Long будет работать во всех случаях
Ключевое слово tailrec заставляет компилятор оптимизировать рекурсию в цикл, и переполненения стека не будет
 */

