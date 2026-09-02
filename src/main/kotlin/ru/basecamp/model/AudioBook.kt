package ru.basecamp.model

data class AudioBook(
    override val title: String,
    override val author: String,
    override val year: Int,
    override val pages: Int,
    override val genre: Genre,
    override val price: Money,
    override val initialCopies: Int,
    override val isbn: String? = null,
    override val edition: String? = null,
    override val originalLanguage: String? = null,
    override val translator: String? = null,
    override val tags: Set<String> = emptySet(),
    val durationMinutes: Int,
    val narrator: String? = null,
    val format: AudioFormat = AudioFormat.MP3
) : Book(
    title = title,
    author = author,
    year = year,
    pages = pages,
    genre = genre,
    price = price,
    initialCopies = initialCopies,
    isbn = isbn,
    edition = edition,
    originalLanguage = originalLanguage,
    translator = translator,
    tags = tags
) {
    override val category: String = "AudioBook"

    init {
        require(durationMinutes > 0) { "Длительность аудио должна быть > 0, а не $durationMinutes" }
    }
}

enum class AudioFormat { MP3, OGG }