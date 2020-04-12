import RPi.GPIO as GPIO
import time
 

RED = 33
YELLOW = 19
GREEN = 13


GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(YELLOW,GPIO.OUT)
GPIO.setup(GREEN,GPIO.OUT)
GPIO.setup(RED,GPIO.OUT)



GPIO.output(RED, GPIO.HIGH)
time.sleep(5)
GPIO.output(RED, GPIO.LOW)
time.sleep(2)
GPIO.output(RED, GPIO.HIGH)
time.sleep(5)
GPIO.cleanup()
