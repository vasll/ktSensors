package com.vasll.parsers

import com.vasll.model.Component
import com.vasll.model.Sensor
import java.util.stream.Collectors


/**
 * Contains functions for parsing the data stream coming from the libreScript.ps1 Powershell script into Component and
 * Sensor objects.
 */
object LibreParser {

    /** Parses a Component from the LHM library */
    fun parseComponent(textBlock: String): Component{
        val textBlockMap = textBlockToMap(textBlock)
        return Component(
            textBlockMap["HardwareType"]?.toComponentType(),
            textBlockMap["Name"],
            arrayListOf()
        )
    }

    /** Parses a Sensor from the LHM library */
    fun parseSensor(textBlock: String): Sensor {
        val textBlockMap = textBlockToMap(textBlock)

        return Sensor(
            textBlockMap["Hardware"],
            textBlockMap["Identifier"],
            textBlockMap["Index"]?.toInt(),
            textBlockMap["Max"]?.tryParseDouble(),
            textBlockMap["Min"]?.tryParseDouble(),
            textBlockMap["Name"],
            textBlockMap["SensorType"]?.toSensorType(),
            textBlockMap["Value"]?.tryParseDouble(),
        )
    }

    /**
     * Parses a block of data from the libreScript.ps1 script into a MutableMap that has the key as the first parameter
     * and the value as the second parameter. In the script a block starts when "|..._BLOCK_START|" is printed in stdout
     * and ends when "|..._BLOCK_END|" is printed in stdout
     * */
    private fun textBlockToMap(textBlock: String): MutableMap<String, String> {
        val mutableMap = mutableMapOf<String, String>()

        for(line in textBlock.split('\n')){
            if(line.startsWith(" ")){
                continue    // TODO add check for longer entries that go to newline
            }

            // Example: ["Name", "Ryzen 5 4600"]
            val keyValuePair: List<String> = line.split(":", limit = 2)
                .stream()
                .map { obj: String -> obj.trim { it <= ' ' } }
                .collect(Collectors.toList())

            if(keyValuePair.size == 2){
                mutableMap.put(keyValuePair[0], keyValuePair[1])
            }
        }

        return mutableMap
    }


    // EXTRA FUNCTIONS
    /** Attempts to parse a String into a Double
     * @return null if NumberFormatException is raised, else the parsed Double
     */
    private fun String.tryParseDouble(): Double? {
        return try{
            // String->Double parsing with commas doesn't work, commas are replaced with dots
            this.replace(',','.').toDouble()
        }catch(ex: NumberFormatException){
            ex.printStackTrace()
            return null
        }
    }

    /** Parses a String coming from the "Type" field in the libreParser.ps1 script into a model.Sensor.Type enum
     * @return null if parsing failed, else the parsed Sensor.Type
     */
    private fun String.toSensorType(): Sensor.Type? {
        for(type in Sensor.Type.values())
            if(type.str == this)
                return type

        return null
    }

    /** Parses a String coming from the "Type" field in the libreParser.ps1 script into a model.Component.Type enum
     * @return null if parsing failed, else the parsed Component.Type
     */
    private fun String.toComponentType(): Component.Type? {
        for(type in Component.Type.values())
            if (type.str == this)
                return type
            else if (this.contains("Gpu"))
                return Component.Type.GPU
        /* GPU components coming from the libreParser.ps1 script have a value of "GpuAmd", "GpuNvidia"... in the "Type"
        * field. For simplicity, I decided to just parse them all as a Component.Type.GPU enum */

        return null
    }
}