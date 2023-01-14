***This project is very WIP***

# How does the library work?
1. Get the text stream from the powershell script
2. Read the stream from the powershell script in real time
3. Parse the stream from the powershell script into Component and Sensor
4. Make a custom streamUpdateListener that gets toggled each time the sensor stream ends and restarts

# TODO
<br>Fundamental

- [ ] Implement subhardware elements
<br>_Right now every subhardware object from the hardwaremonitorlib is treated simply as a hardware Component object_
- [ ] Add linux support

<br>Optional
- [x] Make sensor stream update rate changeable
- [ ] Fix on app close Powershell script still running
