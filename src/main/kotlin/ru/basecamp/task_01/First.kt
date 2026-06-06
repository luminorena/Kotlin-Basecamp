package ru.basecamp.task_01

/**
 * Расширьте чтение книги — добавьте поле isbn: String.
 * Введённую строку нужно очистить (убрать дефисы и
 * пробелы) и проверить:
 * 1. Длина после очистки — ровно 13 символов.
 * 2. Все символы — цифры (используйте Char.isDigit() в цикле или String.all { it.isDigit() }).
 * 3. Подсчитайте контрольную сумму ISBN-13: умножьте каждую цифру на 1
 * (для чётных индексов 0,2,4,...) или 3
 * (для нечётных), сложите. Сумма должна делиться на 10 без остатка.
 */
fun main() {
    print("Введите ISBN книги: ")
    val isbn: String = readln()
    val cleanIsbn = isbn.replace(Regex("[\\s+-]"), "")

    if (cleanIsbn.length != 13 || !cleanIsbn.matches(Regex("\\d+"))) {
        println("ISBN должен быть равен 13-и символам и содержать только цифры")
        return
    }

    var result = 0
    for (i in cleanIsbn.indices) {
        result += if (i % 2 == 0) {
            cleanIsbn[i].digitToInt() * 1
        }
         else {
             cleanIsbn[i].digitToInt() * 3
        }
    }

    if (result % 10 == 0) println("ISBN валиден, контрольная сумма равна $result")
        else println("ISBN не валиден, контрольная сумма равна $result, попробуйте ещё раз")
}