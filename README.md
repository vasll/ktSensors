# ktSensors
Get sensor data from your Windows computer using Java/Kotlin<br>

This library works by wrapping the [librehardwaremonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor) library via a powershell script into kotlin objects (Component, Sensor)

_Only works on Windows, there is no Linux or macOS implementation yet_<br>

## Library usage ##

### Print all the components and sensors ###

This code will print all the components and component sensors that librehardwaremonitor can find, the getComponents() method returns a List\<Component> where each Component contains a List\<Sensor> that can be indexed

```kotlin
while(true){
    for(component in KTSensors.getComponents()){
        println("${component.name} ${component.type}")

        for(sensor in component.sensors){
            println(" ${sensor.name} ${sensor.value}")
        }
    }
}
```

### Get only the CPU temperature sensors ###

This code will print the CPU name and each of its temperature sensors

```kotlin
for(component in KTSensors.getComponents()){
    if(component.type == Component.Type.CPU) {
        println("${component.name} ${component.type}")

        for (sensor in component.sensors) {
            if(sensor.type == Sensor.Type.TEMPERATURE) {
                println(" ${sensor.name} ${sensor.value}")
            }
        }
    }
}
```
