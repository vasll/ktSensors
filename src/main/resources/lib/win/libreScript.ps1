# Load DLLs
[Reflection.Assembly]::LoadFrom($PSScriptRoot+"\dlls\LibreHardwareMonitorLib.dll") | Out-Null
[Reflection.Assembly]::LoadFrom($PSScriptRoot+"\dlls\HidSharp.dll") | Out-Null

. $PSScriptRoot\UpdateVisitor.ps1

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
    $UpdateVisitor = New-Object UpdateVisitor
    $computer.Open()
    $computer.Accept($UpdateVisitor);
}catch{
    Write-Warning $Error[0]
    Exit 69420
}


# Print the sensor data
ForEach ($hardware in $computer.Hardware){ 
    $hardware
    ForEach ($subHardware in $hardware.SubHardware){
        $subHardware
        ForEach ($sensor in $subHardware.Sensors){
            $sensor
        }
    }
    ForEach ($sensor in $hardware.Sensors){
        $sensor
    }
}
$computer.Close()