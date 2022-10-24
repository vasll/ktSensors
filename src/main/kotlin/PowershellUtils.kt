
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder


class PowershellUtils {

    companion object {
        private const val SCRIPT_PATH = "src/main/resources/lib/win/libreScript.ps1"    // Path of the .ps1 script that uses the LibreHardwareMonitor library

        fun runAndGet(): String{
            val scriptProcess = Runtime.getRuntime().exec(arrayOf("Powershell", SCRIPT_PATH))

            val stdInput = BufferedReader(InputStreamReader(scriptProcess.inputStream))
            val stdError = BufferedReader(InputStreamReader(scriptProcess.errorStream))

            val outputBuffer = StringBuilder()

            var s: String?
            while (stdInput.readLine().also { s = it } != null) {
                outputBuffer.append(s).append('\n')
            }

            while (stdError.readLine().also { s = it } != null) {
                outputBuffer.append(s).append('\n')
            }

            return outputBuffer.toString()
        }

    }

}