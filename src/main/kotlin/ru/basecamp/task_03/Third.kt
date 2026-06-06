package ru.basecamp.task_03

import java.math.BigDecimal

/**
 * Задача 3. Float vs Double — ловушка
 * Добавьте небольшой блок «внимание, плавающая точка»:
 *
 * Запустите код, объясните результат в комментарии в коде (одно предложение).
 * В реальном коде — для денег используйте BigDecimal,
 * для научных вычислений — Double с epsilon-сравнением.
 */
fun main() {
    val a = 0.1 + 0.2
    val b = 0.3
    println("a = $a")
    println("b = $b")
    println("a == b ? ${a == b}") // что выведет?
    println("|a - b| < 1e-9 ? ${kotlin.math.abs(a - b) < 1e-9}")

    /*
    Объяснение: выведется false, потому что 0.1 + 0.2 != 0.3 из-за
    особенностей чисел с плавающей точкой. Для сравнения можно использовать
    либо BigDecimal, либо сравнивать по модулю с очень маленьким числом
     */

    val firstNumber = BigDecimal("0.1")
    val secondNumber = BigDecimal("0.2")

    val result = firstNumber + secondNumber
    println(result)
}