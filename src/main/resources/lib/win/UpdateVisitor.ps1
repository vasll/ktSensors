# UpdateVisitor class
class UpdateVisitor: LibreHardwareMonitor.Hardware.IVisitor {

    [void]VisitComputer([LibreHardwareMonitor.Hardware.IComputer]$computer){
        $computer.Traverse($this)
    }

    [void]VisitHardware([LibreHardwareMonitor.Hardware.IHardware]$hardware){
        $hardware.Update()
        ForEach($subHardware in $hardware.SubHardware){
            $subHardware.Accept($this)
        }
    }

    [void]VisitSensor([LibreHardwareMonitor.Hardware.ISensor]$sensor){
        
    }

    [void]VisitParameter([LibreHardwareMonitor.Hardware.IParameter]$parameter){
        
    }
}