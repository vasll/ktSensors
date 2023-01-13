package com.vasll



fun main() {
    val libreStream = LibreStream()

    libreStream.addStreamListener {
        println("Working: $it")
    }
    libreStream.startStream()
}




