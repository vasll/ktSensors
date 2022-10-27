import model.Component
import model.Sensor
import parsers.LibreParser

fun main() {
    println("Getting components...")

    for(component in LibreParser.getComponents()){
        println("${component.name} ${component.hardwareType}")

        for(sensor in component.sensors){
            println(" $sensor")
        }
    }




/*
    while(true){
        for(component in LibreParser.getComponents()){
            if(component.hardwareType == Component.Type.CPU){
                println("${component.name}")
                for(sensor in component.sensors){
                    if(sensor.type == Sensor.Type.TEMPERATURE) {
                        println(" ${sensor.name}: ${sensor.value}°")
                    }
                }
            }
        }
    }
*/
}