import WinUtils.Powershell.LIBRE_SCRIPT
import model.Component
import model.Sensor

/**
 * Parses the output of the LibreHardwareMonitor library into Sensor and Component objects.
 *
 * Each block of information that is delimited by an empty line is considered a block, and it contains information
 * about either a sensor or a hardware component.
 * If you want to see the raw output of libreScript.ps1 use: WinUtils.Powershell.runAndGet(LIBRE_SCRIPT)
 */
class LibreParser {
    /**
     * Enum class for sensor parameters from the LibreHardwareMonitor library
     */
    enum class SensorParameter(val str: String) {
        CONTROL("Control"), HARDWARE("Hardware"),
        IDENTIFIER("Identifier"), INDEX("Index"),
        IS_DEFAULT_HIDDEN("IsDefaultHidden"), MAX("Max"),
        MIN("Min"), NAME("Name"), PARAMETERS("Parameters"),
        SENSOR_TYPE("SensorType"), VALUE("Value"),
        VALUES("Values"), VALUES_TIME_WINDOW("ValuesTimeWindow")
    }

    companion object {
        fun getComponents(): List<Component>{
            if(!WinUtils.User.isAdmin()) {    // Check for Windows admin privileges first
                println("[WARN] No administrator privileges detected, sensor data will be limited.")
            }

            return parse()
        }


        private fun parse(): List<Component>{
            val blocks = WinUtils.Powershell.runAndGet(LIBRE_SCRIPT).trim().split("\n\n")  // Get the raw output from libreScript.ps1 and split it into blocks

            val components = arrayListOf<Component>()

            var currentComponent: Component? = null
            for(block in blocks) {
                if(block.contains("HardwareType")){ // If the current block is a hardware/component block
                    currentComponent = getComponent(block)
                    components.add(currentComponent)
                }else{  // If the current block is a sensor block
                    if (currentComponent != null) {
                        currentComponent.sensors?.add(getSensor(block))
                    }
                }
            }
            return components
        }

        private fun getComponent(block: String): Component{
            val blockMap = blockToMap(block)

            return Component(blockMap["HardwareType"]?.toComponentType(), blockMap["Name"], arrayListOf())
        }


        /**
         * Returns a Sensor from a block of Strings
         */
        private fun getSensor(block: String): Sensor {
            val blockMap = blockToMap(block)

            // Parse the value
            val value = try{
                // TODO for some reason double parsing works on my AMD pc (XX.XX) but on my Intel pc double values use a comma (XX,XX)
                val rawValue = blockMap[SensorParameter.VALUE.str]?.replace(',','.')?.toDouble()
                if(rawValue!! < 0.0001)  // If the value from the LibreHwMonitor library is < 0.0001 we just floor it
                    0.00
                else
                    rawValue
            }catch(ex: NumberFormatException){
                0.00
            }

            return Sensor(
                blockMap[SensorParameter.HARDWARE.str],
                blockMap[SensorParameter.IDENTIFIER.str],
                blockMap[SensorParameter.INDEX.str]?.toInt(),
                blockMap[SensorParameter.NAME.str],
                blockMap[SensorParameter.SENSOR_TYPE.str]?.toSensorType(),
                value,
            )
        }

        /**
         * Gets a block of code from the LibreHardwareMonitor library and gets back a map where its key is the field name
         * and its values are the field values
         */
        private fun blockToMap(block: String): Map<String, String>{
            val map = mutableMapOf<String, String>()

            var lastKey = ""
            for(line in block.split('\n')){
                if(line.startsWith(" ")){
                    map[lastKey] = map[lastKey] + line.trim()
                    continue
                }

                val keyValuePair = line.split(":", limit = 2).trimAll()
                lastKey = keyValuePair[0]
                map[keyValuePair[0]] = keyValuePair[1]
            }

            return map
        }

        private fun String.toComponentType(): Component.Type? {
            for(type in Component.Type.values())
                if(type.str == this)
                    return type

            return null
        }

        /**
         * Gets the Sensor.Type from a String
         */
        private fun String.toSensorType(): Sensor.Type? {
            for(type in Sensor.Type.values())
                if(type.str == this)
                    return type

            return null
        }

        /**
         * Trims all the strings in a List
         * TODO replace with something better
         */
        private fun List<String>.trimAll(): List<String>{
            val mutableList = this.toMutableList()

            for(i in 0 until mutableList.size){
                mutableList[i] = mutableList[i].trim()
            }

            return mutableList.toList()
        }
    }
}