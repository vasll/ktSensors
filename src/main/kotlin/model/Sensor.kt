package model

/**
 * A data class that represents a sensor
 *
 * The sensor from LibreHardwareMonitor has other parameters, but I decided to not include them in my implementation
 * because I find them not useful to this scope
 */
data class Sensor(
    val hardware: String?,
    val identifier: String?,
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
        FACTOR("Factor");   //TODO add storage sensor types (i.e: "Used Space")
    }
}