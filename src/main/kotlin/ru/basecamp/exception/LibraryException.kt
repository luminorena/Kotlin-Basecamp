package ru.basecamp.exception

sealed class LibraryException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)

class BookNotFoundException(val isbn: String) :
    LibraryException("Книга с ISBN $isbn не найдена в каталоге")

class BookAlreadyExistsException(val isbn: String) :
    LibraryException("Книга с ISBN $isbn уже есть в каталоге")