package com.vasll

import com.vasll.listeners.StreamStartListener
import com.vasll.listeners.StreamUpdateListener
import com.vasll.model.Component
import com.vasll.parsers.LibreParser
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @param updateRate The rate at which the data is updated in SECONDS. Update rate below 0.25s is discouraged
 */
class LibreStream(private var updateRate: Double = 1.00) {
    private val streamUpdateListeners = mutableListOf<StreamUpdateListener>()
    private val streamStartListeners = mutableListOf<StreamStartListener>()
    private lateinit var streamThread: LibreStreamThread
    private val libreScriptPath = "src/main/resources/lib/win/libreScript.ps1"

    // STREAM HANDLING
    /** Starts a Thread with a stream from the libreScript.ps1 file that will fetch Sensor and Component objects */
    fun startStream() {
        streamThread = LibreStreamThread()
        streamThread.start()
    }

    /** Requests a stream close */
    fun requestStreamClose(){
        streamThread.isCloseStreamRequested = true
    }

    /** Restarts the script and gives it a new update rate */
    fun setUpdateRate(updateRate: Double){
        this.updateRate = updateRate
        if(streamThread.isAlive){   // If stream is open, close it and start it again
            this.requestStreamClose()
            this.startStream()
        }
    }

    // LIBRE STREAM THREAD
    /**
     * This Thread fetches data from the libreScript.ps1 Powershell script and parses the output to be used in kotlin.
     * New data is fetched by default each second.
     */
    private inner class LibreStreamThread: Thread() {
        @Volatile
        var isCloseStreamRequested = false

        override fun run(){
            val scriptProcess = Runtime.getRuntime().exec(
                arrayOf("Powershell", libreScriptPath, "-delaySeconds $updateRate")
            )
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
                    // Notify streamStartListeners
                    for (listener in streamStartListeners) {
                        listener.onStreamStart()
                    }
                    continue
                }

                if(s.contains("|STREAM_END|")){
                    // Notify streamUpdateListeners
                    for (listener in streamUpdateListeners) {
                        listener.onStreamUpdate(components)
                    }
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
    }


    // STREAM LISTENERS
    /** Adds a streamUpdateListener to the stream that is notified each time a data stream ends making it safe to
     * elaborate the fetched data */
    fun addStreamUpdateListener(streamUpdateListener: StreamUpdateListener) {
        streamUpdateListeners.add(streamUpdateListener)
    }

    /** Adds a streamStartListener to the stream that is notified each time a data stream is started */
    fun addStreamStartListener(streamStartListener: StreamStartListener) {
        streamStartListeners.add(streamStartListener)
    }

}