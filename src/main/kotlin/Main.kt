import model.Component
import model.Sensor

fun main() {
    val components = LibreParser.getComponents()

    for(component in components){
        println("${component.name} ${component.hardwareType}")
        for(sensor in component.sensors!!){
            println(sensor)
        }
    }
}