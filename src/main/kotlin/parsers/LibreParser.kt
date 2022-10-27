package parsers

import utils.WinUtils.Powershell.LIBRE_SCRIPT
import model.Component
import model.Sensor
import utils.WinUtils

/**
 * Parses the output of the LibreHardwareMonitor library into Sensor and Component objects.
 *
 * Each block of information that is delimited by an empty line is considered a block, and it contains information
 * about either a sensor or a hardware component.
 * If you want to see the raw output of libreScript.ps1 use: utils.WinUtils.Powershell.runAndGet(LIBRE_SCRIPT)
 */
class LibreParser {
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
                    currentComponent?.sensors?.add(getSensor(block))
                }
            }
            return components
        }

        /**
         * Attempts to parse a block into a Component
         * @return a Component
         */
        private fun getComponent(block: String): Component{
            val blockMap = blockToMap(block)
            return Component(blockMap["HardwareType"]?.toComponentType(), blockMap["Name"], arrayListOf())
        }


        /**
         * Attempts to parse a block into a Sensor
         * @return a Sensor
         */
        private fun getSensor(block: String): Sensor {
            val blockMap = blockToMap(block)

            // Parse the value
            val value = try{
                // TODO for some reason double parsing works on my AMD pc (XX.XX) but on my Intel pc double values use a comma (XX,XX)
                blockMap["Value"]?.replace(',','.')?.toDouble()
            }catch(ex: NumberFormatException){
                0.00
            }

            return Sensor(
                blockMap["Hardware"],
                blockMap["Index"]?.toInt(),
                blockMap["Name"],
                blockMap["SensorType"]?.toSensorType(),
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
                if(type.str == this){
                    return type
                }else if(this.contains("Gpu")) {
                    return Component.Type.GPU
                }

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