package com.vasll.model

/**
 * Class that represents a sensor from the librehardwaremonitor library
 */
data class Sensor(
    val hardware: String?,
    val identifier: String?,
    val index: Int?,
    val max: Double?,
    val min: Double?,
    val name: String?,
    val type: Type?,
    val value: Double?
) {
    enum class Type(val str: String){
        LOAD("Load"),
        FAN("Fan"),
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