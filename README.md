# weather_app_raspberry
This repository include project on Raspberry Pi with Sense Hat. It includes python files in which temperature, humidity and pressure is measured. User can also display own text in different colors.
There are also a website and android app as interfaces (this was my first time with Java, Android Studio, HTML, CSS etc.).

## This repository includes:
- **Serwer** - includes files to paste on Raspberry Pi.
- **Images** - includes images of interfaces.
- **Android app** - includes app on android made in Android Studio.
- **Website** - includes website wrote in HTML using CSS, Bootstrap and some JS.


## How does it look like?
**Android Studio app screenshots:**

<img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/app_start.PNG" width="290"><img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/app_invalid.PNG" width="290"><img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/app_about.PNG" width="290"><img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/app_measurement.png" width="290"><img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/app_rgb.PNG" width="290"><img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/app_display.PNG" width="290">

**Website:**

<img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/start_page.png" width="870">
<img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/invalid_adress.png" width="870">
<img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/measurement.png" width="870">
<img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/about.png" width="870">
<img src="https://github.com/m-milena/weather_app_raspberry/blob/master/Images/display_text.png" width="870">

## How to run this on your Raspberry Pi with Sense Hat?

First you have to install lighttpd serwer on your Raspberry. Then:
- Copy [lighttpd.conf] to */etc/lighttpd/*.
- Copy Serwer folder to */var/www/html/*.
- Then:
```console
$ cd /ect/lighttpd/
$ sudo lighttpd -t -f lighttpd.conf
$ sudo lighttpd -D -f lighttpd.conf
```
- To check correct working, go to your web browser and type: *192.168.1.15/Serwer/api/v1/czujniki.php*.
- To run Website, run ip_page.html file and type Raspberry Ip adress (in this project it was 192.168.1.15).


[lighttpd.conf]:<https://github.com/m-milena/weather_app_raspberry/blob/master/lighttpd.conf>
