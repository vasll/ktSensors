fun main() {
    println("Getting components...")

    for(component in KTSensors.getComponents()){
        println("${component.name} ${component.hardwareType}")

        for(sensor in component.sensors){
            println(" $sensor")
        }
    }
}