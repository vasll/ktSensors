package components

enum class SensorType(val type: String){
    LOAD("Load"),
    VOLTAGE("Voltage"),
    POWER("Power"),
    CLOCK("Clock"),
    TEMPERATURE("Temperature");
}

data class Sensor(
    val hardware: String,
    val identifier: String,
    val name: String,
    val type: SensorType,
    val index: Int,
    val value: Double
)
