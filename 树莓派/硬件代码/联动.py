import RPi.GPIO as GPIO
import time
 
def init():
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(12, GPIO.IN)
    GPIO.setup(11,GPIO.OUT)
    
    pass

def checkPerson():
    if True:
        open()

def open():
    GPIO.cleanup()
    tilt = 27
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(tilt, GPIO.OUT)
    for i in range (0, 90, 10):
        time.sleep(0.3)
        print(i)
        setServoAngle(tilt, i)
    time.sleep(3)
    for i in range (90, 00, -10):
        time.sleep(0.3)
        print(i)
        setServoAngle(tilt, i)
        
    setServoAngle(tilt, 0)

def setServoAngle(servo, angle):
	pwm = GPIO.PWM(servo, 50)
	pwm.start(8)
	dutyCycle = angle / 18. + 3.
	pwm.ChangeDutyCycle(dutyCycle)
	time.sleep(0.3)
	pwm.stop()
	
def beep():
        for i in range(1,2):
            GPIO.output(11, GPIO.LOW) #
            time.sleep(0.5)
            GPIO.output(11, GPIO.HIGH)
            time.sleep(0.5)
            print ("the Buzzer will make sound")
 
def detct():
    for i in range(1, 31):
        if GPIO.input(12) == True:
            print (time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))+"  Someone is closing!")
            beep()
            checkPerson()
        else:
            GPIO.output(11, GPIO.HIGH)
            print (time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))+"  Noanybody!")
        time.sleep(6) #
time.sleep(2)
init()
detct()
GPIO.cleanup()
