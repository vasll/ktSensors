# Script for getting the raw sensor data from the LibreHardwareMonitor library
# Link:   https://github.com/LibreHardwareMonitor/LibreHardwareMonitor
# Author: https://github.com/vasll

# Load the DLLs (Make sure that the dll folder is in the same directory as the script)
[Reflection.Assembly]::LoadFrom($PSScriptRoot+"\\dlls\\LibreHardwareMonitorLib.dll") | Out-Null
[Reflection.Assembly]::LoadFrom($PSScriptRoot+"\\dlls\\HidSharp.dll") | Out-Null

# Insantiate the computer Object
$computer = New-Object LibreHardwareMonitor.Hardware.Computer
$computer.IsCpuEnabled = $true
$computer.IsGpuEnabled = $false
$computer.IsMemoryEnabled = $false
$computer.IsMotherboardEnabled = $false
$computer.IsControllerEnabled = $false
$computer.IsNetworkEnabled = $false
$computer.IsStorageEnabled = $false
$computer.IsBatteryEnabled = $false

# Try opening
try{
    $computer.Open()
}catch{
    Write-Warning $Error[0]
    Exit 69420
}

# Print the sensor data
ForEach ($hardware in $computer.Hardware){
    $hardware
    $hardware.Update()
    ForEach ($subHardware in $hardware.SubHardware){
        $subHardware
        ForEach ($sensor in $subHardware.Sensors){
            $sensor
        }
    }
    ForEach ($sensor in $hardware.Sensors){
        $sensor
    }
    $computer.Close()
}