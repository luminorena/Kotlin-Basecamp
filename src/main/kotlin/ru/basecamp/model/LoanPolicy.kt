package ru.basecamp.model

import java.lang.reflect.Member

class LoanPolicy(
    val maxBooksPerMember: Int = 3,
    val allowedGenres: Set<Genre> = Genre.entries.toSet()
) {
    fun canLend(member: Member, book: Book, activeLoans: Int): Boolean {
        if (activeLoans >= maxBooksPerMember) return false
        return book.genre in allowedGenres
    }
}