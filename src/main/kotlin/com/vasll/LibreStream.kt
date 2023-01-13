package com.vasll

import com.vasll.model.Component
import com.vasll.parsers.LibreParser
import java.io.BufferedReader
import java.io.InputStreamReader

class LibreStream {
    private val streamListeners = mutableListOf<StreamListener>()
    private lateinit var streamThread: LibreStreamThread

    fun addStreamListener(streamListener: StreamListener) {
        streamListeners.add(streamListener)
    }

    private inner class LibreStreamThread: Thread() {
        @Volatile
        var isCloseStreamRequested = false

        override fun run(){
            val scriptProcess = Runtime.getRuntime().exec(arrayOf("Powershell", "src/main/resources/lib/win/libreScript.ps1"))
            val stdInput = BufferedReader(InputStreamReader(scriptProcess.inputStream)) //TODO add stdErr check
            var s: String
            val currentBlock = StringBuilder()
            val components = arrayListOf<Component>()
            var currentComponent : Component? = null

            while (stdInput.readLine().also { s = it } != null) {
                if(isCloseStreamRequested){
                    return
                }

                if(s.contains("|STREAM_START|")){
                    continue
                }

                if(s.contains("|STREAM_END|")){ // Notify listener
                    for (listener in streamListeners)
                        listener.onStreamUpdate(arrayListOf<Component>())
                    continue
                }

                if(s.contains("|HARDWARE_BLOCK_START|")){
                    continue
                }

                if(s.contains("|HARDWARE_BLOCK_END|")){ // Finished reading Component block, add it to the list
                    currentComponent = LibreParser.parseComponent(currentBlock.toString().trim())
                    println(currentComponent)    //TODO REMOVE
                    components.add(currentComponent)
                    currentBlock.clear()
                    continue
                }

                if(s.contains("|SENSOR_BLOCK_END|")){   // Finished reading Sensor block, add it to the list
                    currentComponent?.sensors?.add(
                        LibreParser.parseSensor(currentBlock.toString().trim())
                    )
                    println(LibreParser.parseSensor(currentBlock.toString().trim()))    //TODO REMOVE
                    currentBlock.clear()
                    continue
                }
                currentBlock.append("$s\n")
            }
        }
    }

    /**
     * Starts a Thread with a stream from the libreScript.ps1 file that will fetch Sensor and Component objects
     */
    fun startStream() {
        streamThread = LibreStreamThread()
        streamThread.start()
    }

    /**
     * Requests a Thread.interrupt() on the Thread on which the stream is running on
     */
    fun closeStream(){
        streamThread.isCloseStreamRequested = true
    }


}