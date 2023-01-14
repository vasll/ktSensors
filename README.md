***This project is very WIP***

# How does the library work?
1. Get the text stream from the powershell script
2. Read the stream from the powershell script in real time
3. Parse the stream from the powershell script into Component and Sensor
4. Make a custom streamListener that gets toggled each time the sensor stream ends and restarts

# TODO
<br>Fundamental

- [ ] Implement subhardware elements
<br>_Right now every subhardware object from the hardwaremonitorlib is treated simply as a hardware Component object_


<br>Optional
- [ ] Add linux support

- [ ] Make sensor stream update rate changeable
<br>_Right now in the libreScript.ps1 script the update rate is at 1s and is not changeable. To change the update rate make a setUpdateRate(var seconds) method that stops the Powershell script, passes it the update rate in seconds as a parameter and restarts the stream._