package com.vasll

fun main() {
    println("Getting components...")

    for(component in KTSensors.getComponents()){
        println("${component.name} ${component.type}")

        for(sensor in component.sensors){
            println(" ${sensor.name} ${sensor.value}")
        }
    }
}