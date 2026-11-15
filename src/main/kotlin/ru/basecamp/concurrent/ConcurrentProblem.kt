package ru.basecamp.concurrent

import kotlin.concurrent.thread

    fun main() {
        var counter = 0
        val threads = List(10) {
            thread {
                repeat(10_000) {
                    counter++
                }
            }
        }
        threads.forEach { it.join() }
        println("Ожидали: 100000, получили: $counter")
    }
