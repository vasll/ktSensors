package model

/**
 * Class that represents a hardware component like a motherboard, CPU, GPU, etc...
 */
//TODO better implementation of this, might have to create CPU object, GPU object etc... since they have unique params
data class Component(
    val hardwareType: Type?,
    val name: String?,
    val sensors: ArrayList<Sensor>
) {
    enum class Type(val str: String){
        CPU("Cpu"),
        GPU("Gpu"),
        GENERIC_MEMORY("Generic Memory"),
        MEMORY("Memory"),
        MOTHERBOARD("Motherboard"),
        NETWORK("Network"),
        STORAGE("Storage"),
        BATTERY("Battery"),
        SUPER_IO("SuperIO")
    }
}