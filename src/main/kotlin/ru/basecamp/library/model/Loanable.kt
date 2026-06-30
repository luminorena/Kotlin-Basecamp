package ru.basecamp.library.model

interface Loanable {
    val isAvailable: Boolean
    fun lend(): Boolean
    fun returnCopy()
    fun describeAvailability(): String =
        if (isAvailable) "Доступно для выдачи" else "Все экземпляры на руках"
}