package ru.basecamp.concurrent

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class ReservationProcessor(private val library: ConcurrentLibrary) {

    private val queue: LinkedBlockingQueue<String> = LinkedBlockingQueue()
    private var workerThread: Thread? = null
    @Volatile private var running = false
    fun start() {
        running = true
        workerThread = thread {
            while (running) {
                val isbn = queue.poll(100, TimeUnit.MILLISECONDS) ?: continue
                println("[Processor] Обрабатываем $isbn: ${library.lend(isbn)}")
            }
        }
    }
    fun reserve(isbn: String) {
        queue.put(isbn)
    }
    fun stop() {
        running = false
        workerThread?.join(1000)
    }
}