from time import sleep
import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

tilt = 27

GPIO.setup(tilt, GPIO.OUT) # white => TILT

def setServoAngle(servo, angle):
	pwm = GPIO.PWM(servo, 50)
	pwm.start(8)
	dutyCycle = angle / 18. + 3.
	pwm.ChangeDutyCycle(dutyCycle)
	sleep(0.3)
	pwm.stop()

if __name__ == '__main__':  
    for i in range (0, 90, 10):
        sleep(0.3)
        print(i)
        setServoAngle(tilt, i)
    sleep(3)
    for i in range (90, 00, -10):
        sleep(0.3)
        print(i)
        setServoAngle(tilt, i)
        
    setServoAngle(tilt, 0)    
    GPIO.cleanup()
