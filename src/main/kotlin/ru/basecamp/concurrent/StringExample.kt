package ru.basecamp.concurrent

import kotlin.concurrent.thread

fun main() {
    val sb = StringBuilder()
    val threads = List(10) {
        thread {
            repeat(1000) { sb.append("x") }
        }
    }
    threads.forEach { it.join() }
    println("StringBuilder: ожидали 10000, получили ${sb.length}")

    val sbf = StringBuffer()
    val threads2 = List(10) {
        thread {
            repeat(1000) { sbf.append("x") }
        }
    }
    threads2.forEach { it.join() }
    println("StringBuffer: ожидали 10000, получили ${sbf.length}")
}