package ru.basecamp.task_04


/**
 * Задача 4 (★ бонус). as? в действии
 * Допустим, вы получаете данные «из внешнего источника» в виде Any (типа JSON-парсера). Реализуйте
 * безопасное извлечение:
 * Проверьте на: parseYear(2008), parseYear("2008"), parseYear("две тысячи восьмой"), parseYear(null),
 * parseYear(2.5).
 * fun parseYear(value: Any?): Int? {
 * // если value — Int, вернуть как есть
 * // если String — попытаться преобразовать в Int (toIntOrNull)
 * // иначе — null
 * // ИСПОЛЬЗУЙТЕ `as?`, не `as`
 * }
 * Проверьте на: parseYear(2008), parseYear("2008"), parseYear("две тысячи восьмой"), parseYear(null),
 * parseYear(2.5).

 */

fun main() {
    println(parseYear(null))
}
fun parseYear(value: Any?): Int? {
    return (value as? Int) ?: (value as? String)?.toIntOrNull()
}

