# Load DLLs
[Reflection.Assembly]::LoadFrom($PSScriptRoot+"\dlls\LibreHardwareMonitorLib.dll") | Out-Null
[Reflection.Assembly]::LoadFrom($PSScriptRoot+"\dlls\HidSharp.dll") | Out-Null

. $PSScriptRoot\UpdateVisitor.ps1

# Instantiate the computer Object
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
    "|STREAM_START|"
    ForEach ($hardware in $computer.Hardware){
        "|HARDWARE_BLOCK_START|"
        "$($hardware|Out-String)".Trim()
        "|HARDWARE_BLOCK_END|"
        ForEach ($subHardware in $hardware.SubHardware){
            "|HARDWARE_BLOCK_START|"
            "$($subHardware|Out-String)".Trim()
            "|HARDWARE_BLOCK_END|"
            ForEach ($sensor in $subHardware.Sensors){
                "|SENSOR_BLOCK_START|"
                "$($sensor|Out-String)".Trim()
                "|SENSOR_BLOCK_END|"
            }
        }
        ForEach ($sensor in $hardware.Sensors){
            "|SENSOR_BLOCK_START|"
            "$($sensor|Out-String)".Trim()
            "|SENSOR_BLOCK_END|"
        }
    }
    "|STREAM_END|"
    Start-Sleep -s 1    #TODO make this changeable
}

$computer.Reset()