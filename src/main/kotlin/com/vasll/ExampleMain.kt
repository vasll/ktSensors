package com.vasll

// Example of LibreStream usage
fun main(){
    val libreStream = LibreStream()

    libreStream.addStreamListener {
        for(component in it){
            println("${component.name} ${component.type}")
            for(sensor in component.sensors){
                println("\t${sensor.type} | ${sensor.name} | ${sensor.value}")
            }
        }
    }

    libreStream.startStream()
}