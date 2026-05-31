package ru.otus.ru.otus.task_03

/**
 *        Задача 3. Учёт выдач (простая арифметика).
 *         После карточки добавьте блок "Статистика выдач".
 *         Объявите две переменные var:
 *             var totalLoans = 0       // сколько раз книгу брали
 *             var currentlyOnHand = 0  // сколько сейчас на руках у читателей
 *         Сымулируйте 3 операции выдачи и 1 возврат —
 *         изменяя var через ++ и -- (или +=/-=):
 *             Выдача №1: на руках 1, всего выдач 1
 *             Выдача №2: на руках 2, всего выдач 2
 *             Выдача №3: на руках 3, всего выдач 3
 *             Возврат:   на руках 2, всего выдач 3
 *             Свободно на полке: <copiesInStock - currentlyOnHand>
 *         Здесь специально нужен var — это редкий, но честный кейс.
 *         Дальше по курсу будем стараться обходиться без них.
 */
fun main() {
    var totalLoans = 0
    var currentlyOnHand = 0
    val copiesInStock = 12

    println("Свободно на полке: ${copiesInStock - currentlyOnHand}")

    currentlyOnHand++
    totalLoans++
    println("Выдача №1: на руках ${currentlyOnHand}, всего выдач: ${totalLoans}")
    println("Свободно на полке: ${copiesInStock - currentlyOnHand}")

    currentlyOnHand++
    totalLoans++
    println("Выдача №2: на руках ${currentlyOnHand}, всего выдач: ${totalLoans}")
    println("Свободно на полке: ${copiesInStock - currentlyOnHand}")

    currentlyOnHand++
    totalLoans++
    println("Выдача №3: на руках ${currentlyOnHand}, всего выдач: ${totalLoans}")
    println("Свободно на полке: ${copiesInStock - currentlyOnHand}")

    currentlyOnHand--
    println("Возврат: на руках ${currentlyOnHand}")
    println("Свободно на полке: ${copiesInStock - currentlyOnHand}")
}