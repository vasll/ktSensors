package com.vasll.parsers

import com.vasll.model.Component
import com.vasll.model.Sensor
import java.util.stream.Collectors


object LibreParser {

    fun parseComponent(textBlock: String): Component{
        val textBlockMap = textBlockToMap(textBlock)
        return Component(
            textBlockMap["HardwareType"]?.toComponentType(),
            textBlockMap["Name"],
            arrayListOf()
        )
    }

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
    private fun String.tryParseDouble(): Double? {
        return try{
            this.replace(',','.').toDouble()
        }catch(ex: NumberFormatException){
            ex.printStackTrace()
            return null
        }
    }

    private fun String.toSensorType(): Sensor.Type? {
        for(type in Sensor.Type.values())
            if(type.str == this)
                return type

        return null
    }

    private fun String.toComponentType(): Component.Type? {
        for(type in Component.Type.values())
            if(type.str == this){
                return type
            }else if(this.contains("Gpu")) {
                /*  GPUS are not called "GPU" but "GpuAmd", "GpuNvidia" etc..., I decided for simplicity to just
                parse them all as a GPU  */
                return Component.Type.GPU
            }

        return null
    }
}