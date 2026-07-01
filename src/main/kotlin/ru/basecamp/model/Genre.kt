package ru.basecamp.model

enum class Genre(val displayName: String) {
    FICTION("Проза"),
    NON_FICTION("Нон-фикшн"),
    FANTASY("Фэнтези"),
    SCIENCE_FICTION("Научная фантастика"),
    DETECTIVE("Детектив"),
    ROMANCE("Роман"),
    HORROR("Ужасы")
}