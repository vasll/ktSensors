***This project is very WIP, use at your own risk***

# How does the library work?
_For now the library only works on Windows_\
A Powershell script is ran with sample code from the [LibreHardwareMonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor) library's README.md, that script then periodically sends sensor data to the Powershell console's output stream, the stream is then parsed into Kotlin objects like Hardware and Sensor and then they can be used into a Kotlin/Java program

# Example usage
If you are on windows you have to use the **LibreStream** Object, then you need to add a streamUpdateListener to it which is toggled each time a sensor data stream from the [LibreHardwareMonitor](https://github.com/LibreHardwareMonitor/LibreHardwareMonitor) library ends, making it safe to use the given Sensor and Component Objects
```kotlin
fun main(){
    val libreStream = LibreStream(1.0)

    libreStream.addStreamUpdateListener {
        for(component in it){
            println("${component.name} ${component.type}")
            for(sensor in component.sensors){
                println("\t${sensor.type} | ${sensor.name} | ${sensor.value}")
            }
        }
    }

    libreStream.startStream()
}
```

# TODO
**Fundamental**
- [ ] Implement subhardware elements - _Right now every subhardware object from the hardwaremonitorlib is treated simply as a hardware Component object_
- [ ] Add linux support
- [ ] On app exit close all the running scripts in the background 

**Optional**
- [x] Make sensor stream update rate changeable
- [ ] Fix on app close Powershell script still running
