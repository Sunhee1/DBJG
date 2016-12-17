import Adafruit_DHT as dht
import MySQLdb
import os
import sys
import time
import pygame


db = MySQLdb.connect(host='192.168.137.94',user='kjh',passwd='1004',db='rpidb')
cursor = db.cursor()

print('db connect')


pin = 22
temp = 24
time_stamp_prev=0

os.environ["SDL_FBDEV"] = "/dev/fb1"
os.environ['SDL_VIDEODRIVER']="fbcon"


#def updatetemp(a):
#  print('update')
#  sql = """update temp set r_temp=(%d) order by num desc limit 1""" %a

#  try:
#    cursor.execute(sql)
#    print("temp db success")
#    db.commit()#

#  except:
#    db.rollback()
#    print("db Fail")


def displaytext(text,size,line,color,clearscreen):
  if clearscreen:
    screen.fill((255,255,255))

  font = pygame.font.Font(None,size)
  text = font.render(text,0,color)
  rotated = pygame.transform.rotate(text,-90)
  textpos = rotated.get_rect()
  textpos.centery = 80
  if line == 1:
    textpos.centerx = 99
    screen.blit(rotated,textpos)
  elif line == 2:
    textpos.centerx = 61
    screen.blit(rotated,textpos)
  elif line == 3:
    textpos.centerx = 25
    screen.blit(rotated,textpos)



global screen
pygame.init()
pygame.mouse.set_visible(0)
size = width,height = 128,160
screen = pygame.display.set_mode(size)


while True :
#      humidity, temperature = dht.read_retry(dht.DHT11, pin)
#      if humidity is not None and temperature is not None:
#         print(temperature)
#         updatetemp(temperature)
         displaytext(time.strftime("%Y.%m.%d"),40,1,(0,0,0),True)
         displaytext(time.strftime("%H:%M:%S"),40,2,(0,0,0),False)
#         displaytext(str(temperature)+'*C',30,3,(0,0,0),False)
         displaytext('10*C',30,3,(0,0,0),False)
         pygame.display.flip()
         time.sleep(1)
         #print "Temp={0:0.1f}*C Humidity={1:0.1f}%".format(temperature, humidity)
#      else:
#         print "Failed to get reading."

