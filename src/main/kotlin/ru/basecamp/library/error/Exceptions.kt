package ru.basecamp.library.error

sealed class LibraryException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)

class CatalogCorruptedException(message: String, cause: Throwable) :
    LibraryException("Каталог повреждён: $message", cause)