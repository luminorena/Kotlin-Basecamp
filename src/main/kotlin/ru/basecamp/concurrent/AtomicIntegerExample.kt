package ru.basecamp.concurrent

import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main() {
    val counter = AtomicInteger(0)
    val threads = List(10) {
        thread {
            repeat(10_000) {
                counter.incrementAndGet()
            }
        }
    }
    threads.forEach { it.join() }
    println("С AtomicInteger: ${counter.get()}")
}