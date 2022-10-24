import WinUtils.Powershell.LIBRE_SCRIPT
import model.Component
import model.Sensor
import java.util.*

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
                if(block.contains("HardwareType")){ // If the current block is a hardware/component block
                    //val component = getComponent(block)
                    //println(component)
                }else{  // If the current block is a sensor block
                    val sensor = getSensor(block)
                    println(sensor)
                }
            }
        }

        private fun getComponent(block: String): Component? {
            return null
        }

        /**
         * Returns a Sensor from a block of Strings
         */
        private fun getSensor(block: String): Sensor {
            val lines = block.split('\n').toMutableList()

            for(i in 0 until lines.size){
                lines[i] = lines[i].split(":", limit = 2)[1].trim()
            }

            return Sensor(
                lines[SensorParameter.HARDWARE.ordinal],
                lines[SensorParameter.IDENTIFIER.ordinal],
                lines[SensorParameter.INDEX.ordinal].toInt(),
                lines[SensorParameter.NAME.ordinal],
                lines[SensorParameter.SENSOR_TYPE.ordinal].toSensorType(),
                lines[SensorParameter.VALUE.ordinal].toDouble(),
            )
        }

        private fun String.toSensorType(): Sensor.Type? {
            for(type in Sensor.Type.values())
                if(type.str == this)
                    return type

            return null
        }
    }
}