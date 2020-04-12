import RPi.GPIO as GPIO
import time
 
def init():
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(12, GPIO.IN)
    GPIO.setup(11,GPIO.OUT)
    pass
def beep():
        for i in range(1,6):
            GPIO.output(11, GPIO.LOW) #
            time.sleep(0.5)
            GPIO.output(11, GPIO.HIGH)
            time.sleep(0.5)
            print "the Buzzer will make sound"
 
def detct():
    for i in range(1, 31):
        if GPIO.input(12) == True:
            print time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))+"  Someone is closing!"
            beep()
        else:
            GPIO.output(11, GPIO.HIGH)
            print time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))+"  Noanybody!"
        time.sleep(6) #
time.sleep(2)
init()
detct()
GPIO.cleanup()