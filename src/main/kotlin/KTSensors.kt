import model.Component
import parsers.LibreParser

class KTSensors {

    companion object{
        fun getComponents(): List<Component>{
            val os = System.getProperty("os.name").lowercase()

            if(os.contains("win")){ // If os is windows
                return LibreParser.getComponents()
            }else if(os.contains("nix") || os.contains("nux") || os.contains("aix")){
                println("[WARN] Linux is not supported yet")
                // TODO linux parser
            }

            println("[WARN] Detected OS is not compatible with this library. Only Windows and Linux are supported.")
            return listOf()
        }
    }

}