package model

/**
 * A data class that represents a hardware component like a motherboard, CPU, GPU, etc...
 */
data class Component(
    val type: Type?,
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