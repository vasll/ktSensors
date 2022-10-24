package model

/**
 * Class that represents a hardware component like a motherboard, CPU, GPU, etc...
 */
//TODO better implementation of this, might have to create CPU object, GPU object etc... since they have unique params
class Component(
    val hardwareType: Type?,
    val name: String?
) {
    enum class Type(val str: String){
        CPU("Cpu"),
        GPU("Gpu"),
        RAM("Memory"),
        MOBO("Motherboard"),
        NETWORK("Network"),
        STORAGE("Storage") //TODO add battery and test on laptops
    }
}