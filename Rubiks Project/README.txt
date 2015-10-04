Running the Program
-------------------
Run the Rubicks_Cube_Solver.exe in application.windows32

Instructions
------------
1. Plug in Arduino
2. Plug in Camera
3. Run Program

Controls
--------
Q - Reset Cube
W - Virtual Scramble
S - Solve

R - Right
L - Left
F - Front
B - Back
D - Down
U - Up

SHIFT + 'Letter' - CCW of that face

C - Open Camera
Z - Close Camera
Space - Capture Face

Running the Code off of Processing if EXE doesn't work
----------------------------------

1. Open Arduino
2. Open StandardFirmata
3. Plug in the USB cable into the Arduino Uno
4. Upload code to Arduino Uno

5. Open Processing 1.5.1 (Must be this version)
6. Open any pde files (eg. Rubicks_Cube_Solver.pde)
7. Press run!
8. Determine the COM port of the Arduino in Device Manager
9. Determine the # in the output '[#] COM Port'

eg.
if the output of the program is the following:
[0] COM_PORT1
[1] COM_PORT4
and if the COM port of the arduino is com_port4 in device manager
# = 1

10. Put that # into comPort in the 2nd line of the code
11. Now run and everything should work
 


