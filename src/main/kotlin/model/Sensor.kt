package model

/**
 * A data class that represents a sensor
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
        FACTOR("Factor");
    }
}