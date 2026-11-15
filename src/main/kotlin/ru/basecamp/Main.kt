import ru.basecamp.concurrent.ConcurrentLibrary
import ru.basecamp.concurrent.ReservationProcessor
import ru.basecamp.io.LoanResult
import ru.basecamp.model.Genre
import ru.basecamp.model.Money
import ru.basecamp.model.PrintedBook
import java.util.Collections
import kotlin.concurrent.thread

fun main() {
    val lib = ConcurrentLibrary("Stress")
    val book = PrintedBook("Test", "Test", 2020, 120, Genre.OTHER, Money(0.0), 3,  isbn = "1234567890123")
    lib.addBook(book)
    val results = Collections.synchronizedList(mutableListOf<LoanResult>())
    val threads = List(5) { idx ->
        thread {
            val r = lib.lend("1234567890123")
            results.add(r)
            println("Thread $idx: $r")
        }
    }
    threads.forEach { it.join() }
    val successes = results.count { it is LoanResult.Success }
    val failures = results.size - successes
    println("\nВсего успешных: $successes")
    println("Всего отказов: $failures")
    check(successes == 3) { "Ожидалось 3 успешных, получили $successes" }

    val processor = ReservationProcessor(lib)

    processor.start()
    processor.reserve("9785916719892")
    processor.reserve("9785170123469")
    Thread.sleep(500)
    processor.stop()
}