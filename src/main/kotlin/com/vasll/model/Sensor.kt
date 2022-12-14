package com.vasll.model

/**
 * A data class that represents a sensor
 */
data class Sensor(
    val hardware: String?,
    val index: Int?,
    val name: String?,
    val type: Type?,
    val value: Double?
) {
    enum class Type(val str: String){
        LOAD("Load"),
        VOLTAGE("Voltage"),
        POWER("Power"),
        CLOCK("Clock"),
        TEMPERATURE("Temperature"),
        CURRENT("Current"),
        CONTROL("Control"),
        THROUGHPUT("Throughput"),
        DATA("Data"),
        SMALL_DATA("SmallData"),
        TIME_SPAN("TimeSpan"),
        LEVEL("Level"),
        ENERGY("Energy"),
        FACTOR("Factor");
    }
}