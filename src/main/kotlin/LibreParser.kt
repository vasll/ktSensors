import WinUtils.Powershell.LIBRE_SCRIPT

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
                    parseSensorBlock(block)
                }
            }
        }

        /**
         * Returns a Map containing a sensor block's data
         */
        private fun parseSensorBlock(block: String): Map<String, String> {
            val dataMap = mutableMapOf<String, String>()

            var parameterIndex = 0
            for(line in block.split('\n')){ // For every line in the sensor block
                dataMap[SensorParameter.values()[parameterIndex].str] = line.split(":", limit = 2)[1].trim()   // Add the corresponding value to the key
                parameterIndex += 1
            }

            println(dataMap["SensorType"])

            return mapOf()
        }
    }
}