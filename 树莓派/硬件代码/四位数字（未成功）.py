#### encoding: utf-8
 
import RPi.GPIO
import time
 
### 定义单个数码管各段led对应的GPIO
LED_A = 16
LED_B = 18
LED_C = 22
LED_D = 32
LED_E = 33
LED_F = 35
LED_G = 36
LED_DP = 37
 
### 定义1到4号数码管阳极对应的GPIO
DIGIT1 = 11
DIGIT2 = 12
DIGIT3 = 13
DIGIT4 = 15
 

RPi.GPIO.setmode(RPi.GPIO.BCM)
 
RPi.GPIO.setup(LED_A, RPi.GPIO.OUT)
RPi.GPIO.setup(LED_B, RPi.GPIO.OUT)
RPi.GPIO.setup(LED_C, RPi.GPIO.OUT)
RPi.GPIO.setup(LED_D, RPi.GPIO.OUT)
RPi.GPIO.setup(LED_E, RPi.GPIO.OUT)
RPi.GPIO.setup(LED_F, RPi.GPIO.OUT)
RPi.GPIO.setup(LED_G, RPi.GPIO.OUT)
RPi.GPIO.setup(LED_DP, RPi.GPIO.OUT)
RPi.GPIO.setup(DIGIT1, RPi.GPIO.OUT)
RPi.GPIO.setup(DIGIT2, RPi.GPIO.OUT)
RPi.GPIO.setup(DIGIT3, RPi.GPIO.OUT)
RPi.GPIO.setup(DIGIT4, RPi.GPIO.OUT)
 
RPi.GPIO.output(DIGIT1, True)
RPi.GPIO.output(DIGIT2, True)
RPi.GPIO.output(DIGIT3, True)
RPi.GPIO.output(DIGIT4, True)
 

def showDigit(no, num, showDotPoint):
    # 先将正极拉低，关掉显示
	RPi.GPIO.output(DIGIT1, False)
	RPi.GPIO.output(DIGIT2, False)
	RPi.GPIO.output(DIGIT3, False)
	RPi.GPIO.output(DIGIT4, False)
	
	if (num == 0) :
		RPi.GPIO.output(LED_A, False)
		RPi.GPIO.output(LED_B, False)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, False)
		RPi.GPIO.output(LED_E, False)
		RPi.GPIO.output(LED_F, False)
		RPi.GPIO.output(LED_G, True)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 1) :
		RPi.GPIO.output(LED_A, True)
		RPi.GPIO.output(LED_B, False)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, True)
		RPi.GPIO.output(LED_E, True)
		RPi.GPIO.output(LED_F, True)
		RPi.GPIO.output(LED_G, True)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 2) :
		RPi.GPIO.output(LED_A, False)
		RPi.GPIO.output(LED_B, False)
		RPi.GPIO.output(LED_C, True)
		RPi.GPIO.output(LED_D, False)
		RPi.GPIO.output(LED_E, False)
		RPi.GPIO.output(LED_F, True)
		RPi.GPIO.output(LED_G, False)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 3) :
		RPi.GPIO.output(LED_A, False)
		RPi.GPIO.output(LED_B, False)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, False)
		RPi.GPIO.output(LED_E, True)
		RPi.GPIO.output(LED_F, True)
		RPi.GPIO.output(LED_G, False)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 4) :
		RPi.GPIO.output(LED_A, True)
		RPi.GPIO.output(LED_B, False)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, True)
		RPi.GPIO.output(LED_E, True)
		RPi.GPIO.output(LED_F, False)
		RPi.GPIO.output(LED_G, False)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 5) :
		RPi.GPIO.output(LED_A, False)
		RPi.GPIO.output(LED_B, True)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, False)
		RPi.GPIO.output(LED_E, True)
		RPi.GPIO.output(LED_F, False)
		RPi.GPIO.output(LED_G, False)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 6) :
		RPi.GPIO.output(LED_A, False)
		RPi.GPIO.output(LED_B, True)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, False)
		RPi.GPIO.output(LED_E, False)
		RPi.GPIO.output(LED_F, False)
		RPi.GPIO.output(LED_G, False)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 7) :
		RPi.GPIO.output(LED_A, False)
		RPi.GPIO.output(LED_B, False)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, True)
		RPi.GPIO.output(LED_E, True)
		RPi.GPIO.output(LED_F, True)
		RPi.GPIO.output(LED_G, True)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 8) :
		RPi.GPIO.output(LED_A, False)
		RPi.GPIO.output(LED_B, False)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, False)
		RPi.GPIO.output(LED_E, False)
		RPi.GPIO.output(LED_F, False)
		RPi.GPIO.output(LED_G, False)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	elif (num == 9) :
		RPi.GPIO.output(LED_A, False)
		RPi.GPIO.output(LED_B, False)
		RPi.GPIO.output(LED_C, False)
		RPi.GPIO.output(LED_D, False)
		RPi.GPIO.output(LED_E, True)
		RPi.GPIO.output(LED_F, False)
		RPi.GPIO.output(LED_G, False)
		RPi.GPIO.output(LED_DP, not showDotPoint)
	
	if (no == 1) :
		RPi.GPIO.output(DIGIT1, True)
	elif (no == 2) :
		RPi.GPIO.output(DIGIT2, True)
	elif (no == 3) :
		RPi.GPIO.output(DIGIT3, True)
	elif (no == 4) :
		RPi.GPIO.output(DIGIT4, True)
 
showDigit(1, 2, False)
time.sleep(5)
### 清理GPIO口
RPi.GPIO.cleanup()