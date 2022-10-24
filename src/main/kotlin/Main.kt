import WinUtils.Powershell.LIBRE_SCRIPT

fun main() {
    if(!WinUtils.User.isAdmin()){
        println("[WARN] No administrator privileges detected, sensor data will be limited.")
    }

    println(WinUtils.Powershell.runAndGet(LIBRE_SCRIPT))
}