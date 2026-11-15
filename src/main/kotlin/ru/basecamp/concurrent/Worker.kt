import kotlin.concurrent.thread

class Worker {
    @Volatile var stop: Boolean = false
    fun run() {
        thread {
            while (!stop) { /* busy work */ }
            println("Stopped")
        }
    }
}

fun main() {
    val worker = Worker()
    worker.run()
    Thread.sleep(100)
    worker.stop = true
    Thread.sleep(500)
    println("Main: stop = ${worker.stop}, but worker may still loop")
}