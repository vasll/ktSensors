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
    "|UPDATE_VISITOR_EXCEPTION|"
    Exit 69420
}


# Print the sensor data
while($true){
    "|SENSOR_STREAM_START|"
    ForEach ($hardware in $computer.Hardware){
        "|BLOCK_START|"
        $hardware
        "|BLOCK_END|"
        ForEach ($subHardware in $hardware.SubHardware){
            "|BLOCK_START|"
            $subHardware
            "|BLOCK_END|"
            ForEach ($sensor in $subHardware.Sensors){
                "|BLOCK_START|"
                $sensor
                "|BLOCK_END|"
            }
        }
        ForEach ($sensor in $hardware.Sensors){
            "|BLOCK_START|"
            $sensor
            "|BLOCK_END|"
        }
    }
    "|SENSOR_STREAM_END|"
}

$computer.Reset()