from pyfirmata import Arduino, util
import time
import MySQLdb
import datetime
import pygame

board = Arduino('/dev/ttyACM0')

pin_button  = board.get_pin('d:9:i')
pin_motor = board.get_pin('d:8:o')
pin_sound = board.get_pin('a:0:i')

flag = 0

it = util.Iterator(board)
it.start()

pin_button.enable_reporting()
pin_sound.enable_reporting()

db = MySQLdb.connect(host='192.168.137.94', user='kjh', passwd ='1004', db='rpidb')
cursor = db.cursor()

##sound 
#pygame.mixer.init()
#bang = pygame.mixer.Sound('/home/pi/Downloads/woo.wav')


print('db connect')

pi_time = 0


def insertsensor(a):
  print('insert sensor')

  if a >= 497 :
    sql = """insert into sound(sound) value (1)"""
  else :
    sql = """insert into sound(sound) value (0)"""

  try:
    cursor.execute(sql)
    print("sound db success")
    db.commit()
  except:
    db.rollback()



def testvalue(a) :
  sql = ('select unix_timestamp(time), max(num) from sound')
  cursor.execute(sql)
  rows = cursor.fetchall()

  for row in rows:

   cur_time = row[0]
   ts = int(time.time())

   print(ts)

   if ts - cur_time >= 5:
      print('same')
      insertsensor(a)

   else :
      print('diff')


print('motor start')


#while pin_button.read() is None:
#      pin_motor.write(True)
#      pass


while True:
   while flag ==0 :
     print('button')
     if pin_button.read():
      print(pin_button.read())
      pin_motor.write(False)
#      bang.play()
#      flag = 1

     else :
      pin_motor.write(True)
      print(pin_button.read())
      flag = 1

   while flag == 1 :
      print('soundstart')
      flag = 0
      if pin_sound.read() :
         print('succ')
         sound = abs(pin_sound.read()*1000.0-547.0)
         flag = 0
         print(sound)
          
#         if sound > 50 :
         chk_time = int(time.time())
#           print('pitime')
#           print(pi_time)#
#	   print('chk_time')
#           print(chk_time)
#           print(sound)

         if chk_time - pi_time >= 5 :
              testvalue(sound)
              pi_time = chk_time
 	 else :
	      print('false')

