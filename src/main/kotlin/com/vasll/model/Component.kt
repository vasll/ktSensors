package com.vasll.model

/**
 * Class that represents a component from the librehardwaremonitor library
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