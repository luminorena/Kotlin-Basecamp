package ru.basecamp.task_03

/**
 * Задача 3. Сценарий выдач (циклы)
 * После карточки и валидации запустите небольшой симулятор выдач. Программа
 * спрашивает:
 * Сколько раз выдать книгу? <число>
 * Затем repeat(N) { ... } симулирует выдачи. На каждой итерации:
 * q если currentlyOnHand < copiesInStock — выдать (увеличить currentlyOnHand
 * и totalLoans),
 * вывести "Выдача N: на руках X из Y"
 * q если все экземпляры уже на руках — вывести "Выдача N: отказ — все экземпляры
 * заняты" и прервать
 * цикл через break (намекаем: break работает только в for/while,
 * в repeat нужен помеченный
 * return@repeat, либо просто for (i in 1..n) — выберите сами
 * и обоснуйте в комментарии).
 * q после цикла — финальная статистика: всего выдач,
 * сколько на руках, сколько свободно.
 */
fun main() {

    var totalLoans = 0
    var currentlyOnHand = 0
    val copiesInStock = 4

    println("Сколько раз выдать книгу ?")
    val issuesQuantity: Int = readln().toInt()

    for(i in 1..issuesQuantity) {
        if (currentlyOnHand < copiesInStock) {
            currentlyOnHand++
            totalLoans++
            println("Выдача $i: на руках $currentlyOnHand из $copiesInStock")
        }
        else {
            println("Выдача $i: отказ — все экземпляры заняты")
            break
        }
    }

    val freeBooks = copiesInStock - currentlyOnHand

    println("""
        Финальная статистика: всего выдач $totalLoans, на руках находятся $currentlyOnHand книг,
        свободно $freeBooks книг
        """)
}
