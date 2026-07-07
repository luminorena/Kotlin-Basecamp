package ru.basecamp.library.model

sealed class LoanResult {
    data object Success : LoanResult()
    data object NotAvailable : LoanResult()
}

