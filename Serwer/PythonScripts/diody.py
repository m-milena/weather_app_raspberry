from sense_hat import SenseHat

import sys

program_name=sys.argv[0]
sense = SenseHat()
text_display=""
for x in range(4, len(sys.argv)):
  text_display=text_display+sys.argv[x]+" "


R=int(sys.argv[1])
G=int(sys.argv[2])
B=int(sys.argv[3])
color = (R,G,B)


sense.show_message(text_display, text_colour=color)




