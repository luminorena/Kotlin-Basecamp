package ru.basecamp.task_02

/**
 * Перепишите printBookCard, чтобы он корректно обрабатывал nullable-поля:
 * === Карточка книги ===
 * Название: Чистый код
 * Автор: Роберт Мартин
 * Год: 2008
 * Страниц: 464
 * Цена: 1290.50 руб.
 * Издание: не указано
 * ISBN: 9785916719892
 * Язык оригинала: английский
 * Переводчик: М. Павлов
 * В наличии: 3 экз.
 * ======================
 * Требования:
 * q Используйте Elvis для дефолтных значений: edition ?: "не указано".
 * q Используйте safe call: originalLanguage?.let { "Язык оригинала: $it" } или эквивалент.
 * q Запрещено использовать !!. Если рука тянется — значит вы ошиблись с типом!
 * q Запрещено проверять через if (x != null) подряд для всех полей — это идиома из Java. Делайте через ?:/?.let.
 * Если поля нет, строку не выводим вовсе:
 * Название: Современная философия
 * Автор: К. Шмид
 * Год: 2021
 * Страниц: 320
 * Цена: 999.00 руб.
 * В наличии: 1 экз.
 *
 *

 */
fun main() {
    printBookCard("Война и мир", "Лев Толстой", 1869, 1225, 799.90, 3, edition = "1234")
}

fun printBookCard(title: String,
                  author: String,
                  year: Int,
                  pages: Int,
                  price: Double,
                  quantity: Int,
                  isbn: String? = null,
                  originalLanguage: String? = null,
                  translator: String? = null,
                  edition: String? = null) {

    val isbn = isbn?.let { "ISBN: $isbn" } ?: ""
    val language = originalLanguage?.let { "Язык оригинала: $originalLanguage" } ?: ""
    val translator = translator?.let { "Переводчик: $translator" } ?: ""
    val edition = edition?.let { "Издательство: $edition" } ?: ""
        println("""
           Карточка книги:
           Название: $title
           Автор:$author
           Год: $year
           Количество страниц: $pages
           Цена: $price
           Количество: $quantity
           $isbn
           $language
           $translator
           $edition
           """.trimIndent()
            .replace(Regex("\n{2,}"), "\n"))
}