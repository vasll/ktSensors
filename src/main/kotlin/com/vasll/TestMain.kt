package com.vasll

import com.vasll.model.Component
import com.vasll.parsers.LibreParser
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    getStream()
}

fun getStream(){
    val scriptProcess = Runtime.getRuntime().exec(arrayOf("Powershell", "src/main/resources/lib/win/libreScript.ps1"))

    val stdInput = BufferedReader(InputStreamReader(scriptProcess.inputStream)) //TODO add stdErr check

    var s: String
    val currentBlock = StringBuilder()


    val components = arrayListOf<Component>()
    var currentComponent : Component? = null

    while (stdInput.readLine().also { s = it } != null) {
        if(s.contains("|STREAM_START|")){
            println("STREAM START")
            continue
        }

        if(s.contains("|STREAM_END|")){
            println("STREAM END")
            continue
        }

        if(s.contains("|HARDWARE_BLOCK_START|")){
            continue
        }

        if(s.contains("|HARDWARE_BLOCK_END|")){ // Finished reading Component block, add it to the list
            currentComponent = LibreParser.parseComponent(currentBlock.toString().trim())
            components.add(currentComponent)
            currentBlock.clear()
            continue
        }

        if(s.contains("|SENSOR_BLOCK_END|")){   // Finished reading Sensor block, add it to the list
            currentComponent?.sensors?.add(
                LibreParser.parseSensor(currentBlock.toString().trim())
            )
            currentBlock.clear()
            continue
        }
        currentBlock.append("$s\n")
    }
}