import WinUtils.Powershell.LIBRE_SCRIPT
import model.Sensor

/**
 * Parses the output of the LibreHardwareMonitor library into Sensor and Component objects.
 *
 * Each block of information that is delimited by an empty line is considered a block, and it contains information
 * about either a sensor or a hardware component.
 * If you want to see the raw output of libreScript.ps1 use: WinUtils.Powershell.runAndGet(LIBRE_SCRIPT)
 */
class LibreParser {

    enum class SensorParameter(val str: String) {
        CONTROL("Control"), HARDWARE("Hardware"),
        IDENTIFIER("Identifier"), INDEX("Index"),
        IS_DEFAULT_HIDDEN("IsDefaultHidden"), MAX("Max"),
        MIN("Min"), NAME("Name"), PARAMETERS("Parameters"),
        SENSOR_TYPE("SensorType"), VALUE("Value"),
        VALUES("Values"), VALUES_TIME_WINDOW("ValuesTimeWindow")
    }

    companion object {
        fun parse() {
            val rawOutput = WinUtils.Powershell.runAndGet(LIBRE_SCRIPT).trim()

            for(block in rawOutput.split("\n\n")) {
                if(!block.contains("HardwareType")){
                    println(getSensor(block))
                }
            }
        }

        private fun String.toSensorType(): Sensor.Type? {
            for(type in Sensor.Type.values())
                if(type.str == this)
                    return type

            return null
        }

        /**
         * Returns a Sensor from a block of Strings
         */
        private fun getSensor(block: String): Sensor {
            val dataMap = mutableMapOf<String, String>()

            var parameterIndex = 0
            for(line in block.split('\n')){ // For every line in the sensor block
                dataMap[SensorParameter.values()[parameterIndex].str] = line.split(":", limit = 2)[1].trim()   // Add the corresponding value to the key
                parameterIndex += 1
            }

            return Sensor(
                dataMap[SensorParameter.HARDWARE.str],
                dataMap[SensorParameter.IDENTIFIER.str],
                dataMap[SensorParameter.INDEX.str]?.toInt(),
                dataMap[SensorParameter.NAME.str],
                dataMap[SensorParameter.SENSOR_TYPE.str]?.toSensorType(),
                dataMap[SensorParameter.VALUE.str]?.toDouble(),
            )
        }
    }
}