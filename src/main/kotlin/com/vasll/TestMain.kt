package com.vasll



fun main() {
    val libreStream = LibreStream()

    libreStream.addStreamListener {
        println("Working: $it")
    }
    libreStream.startStream()

    try {
        Thread.sleep(5000)
    } catch (ignored: Exception) {}
    libreStream.requestStreamClose()
    println("Stopped calculation stream")
}




