#!/usr/bin/python
from sense_hat import SenseHat
import sys, getopt, json
sense = SenseHat()
myHumidRead = sense.get_humidity()
myTempRead = sense.get_temperature()
myPressRead = sense.get_pressure()
orientation = sense.get_orientation_degrees()
PITCH = orientation['pitch']
ROLL = orientation['roll']
YAW = orientation['yaw']

result={'Temperatura' :{'id': 0,'wartosc': myTempRead, 'jednostka':'C', 'dokladnoscOdczytu':2, 'zakresPracyMin':-10,
'zakresPracyMax':40, 'opis':'pomiar temperatury powietrza'},
'Cisnienie': {'id': 1, 'wartosc':myPressRead, 'jednostka': 'hPa', 'dokladnoscOdczytu': 2, 'zakresPracyMin':-10,
'zakresPracyMax':30, 'opis':'pomiar cisnienia atmosferycznego'},
'Wilgotnosc': {'id': 3, 'wartosc':myHumidRead, 'jednostka': '%', 'dokladnoscOdczytu': 2, 'zakresPracyMin':-10,
'zakresPracyMax':30, 'opis':'pomiar wilgotnosci atmosferycznego'},
'Zyroskop': {'id': 2, 'wartosc':{'r':ROLL, 'p':PITCH, 'y':YAW}, 'dokladnoscOdczytu': 2, 'opis':'pomiar orientacji sense hat'}
 } 
print json.dumps(result)
