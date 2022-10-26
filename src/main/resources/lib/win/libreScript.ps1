# Script for getting the raw sensor data from the LibreHardwareMonitor library
# Link:   https://github.com/LibreHardwareMonitor/LibreHardwareMonitor
# Author: https://github.com/vasll

# Load the DLLs (Make sure that the dll folder is in the same directory as the script)
[Reflection.Assembly]::LoadFrom($PSScriptRoot+"\\dlls\\LibreHardwareMonitorLib.dll") | Out-Null
[Reflection.Assembly]::LoadFrom($PSScriptRoot+"\\dlls\\HidSharp.dll") | Out-Null

# Insantiate the computer Object
$computer = New-Object LibreHardwareMonitor.Hardware.Computer
$computer.IsCpuEnabled = $true
$computer.IsGpuEnabled = $true
$computer.IsMemoryEnabled = $true
$computer.IsMotherboardEnabled = $true
$computer.IsControllerEnabled = $true
$computer.IsNetworkEnabled = $false
$computer.IsStorageEnabled = $true
$computer.IsBatteryEnabled = $true

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
    try{
        $hardware.Update()
    }catch{
        continue
        #TODO: Here an exception is being ignored be aware
        #Write-Warning $Error[0]
        #Exit 69420
    }
    
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