package ru.basecamp.task_04

import java.math.BigInteger

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

