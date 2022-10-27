package utils
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Utilities for Windows
 */
object WinUtils{
    object User{
        fun isAdmin(): Boolean {
            //TODO This is a temporary & not tested solution
            try {
                val adminQuery = "reg query \"HKU\\S-1-5-19\""  //
                val adminCheckerProcess = Runtime.getRuntime().exec(adminQuery)
                adminCheckerProcess.waitFor()

                val exitValue = adminCheckerProcess.exitValue()
                return (0 == exitValue) // If exit value is 0 user is admin
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }
    }

    object Powershell{
        const val LIBRE_SCRIPT = "src/main/resources/lib/win/libreScript.ps1"    // Path of the .ps1 script that uses the LibreHardwareMonitor library

        /**
         * Runs a Powershell script and gets back the output as a String
         * @param scriptPath The path of the Powershell script
         * @return The output of the Powershell script (if any)
         */
        fun runAndGet(scriptPath: String): String{
            val scriptProcess = Runtime.getRuntime().exec(arrayOf("Powershell", scriptPath))

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