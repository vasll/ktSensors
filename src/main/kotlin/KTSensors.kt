import model.Component
import parsers.LibreParser

object KTSensors {
    /**
     * Gets a List of hardware components
     * @return a List of hardware components
     */
    fun getComponents(): List<Component>{
        val os = System.getProperty("os.name").lowercase()

        if(os.contains("win")){ // If os is windows
            return LibreParser.getComponents()
        }else if(os.contains("nix") || os.contains("nux") || os.contains("aix")){ // If os is linux
            println("[WARN] Linux is not supported yet")
            return listOf() // TODO linux parser
        }

        println("[WARN] Detected OS is not compatible with this library. Only Windows and Linux are supported.")
        return listOf()
    }
}