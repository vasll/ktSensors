package model

/**
 * A data class that represents a sensor
 */
data class Sensor(
    val hardware: String,
    val identifier: String,
    val name: String,
    val type: Type,
    val index: Int,
    val value: Double
) {
    enum class Type(val type: String){
        LOAD("Load"),
        VOLTAGE("Voltage"),
        POWER("Power"),
        CLOCK("Clock"),
        TEMPERATURE("Temperature");
    }
}