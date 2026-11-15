package ru.basecamp.concurrent

import kotlin.concurrent.thread

fun main() {
    var counter = 0
    val lock = Any()
    val threads = List(10) {
        thread {
            repeat(10_000) {
                synchronized(lock) {
                    counter++
                }
            }
        }
    }
    threads.forEach { it.join() }
    println("С synchronized: $counter")
}