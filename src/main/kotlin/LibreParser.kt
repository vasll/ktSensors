import WinUtils.Powershell.LIBRE_SCRIPT

/**
 * Parses the output of the LibreHardwareMonitor library into Sensor and Component objects.
 *
 * Each block of information that is delimited by an empty line is considered a block, and it contains information
 * about either a sensor or a hardware component.
 * If you want to see the raw output of libreScript.ps1 use: WinUtils.Powershell.runAndGet(LIBRE_SCRIPT)
 */
class LibreParser {

    companion object {
        fun parse() {
            val rawOutput = WinUtils.Powershell.runAndGet(LIBRE_SCRIPT).trim()

            for(block in rawOutput.split("\n\n")) {
                println("============== START OF BLOCK ==============")

                if(!block.contains("HardwareType")){
                    println(block)
                }

                println("=============== END OF BLOCK ===============")
            }
        }

        fun getBlockParams(){

        }
    }
}