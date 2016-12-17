import RPi.GPIO as gpio
import time
import MySQLdb
import datetime

db = MySQLdb.connect(host='192.168.137.94',user='kjh',passwd='1004',db='rpidb')
cursor = db.cursor()
  
print('db connect')

gpio.setmode(gpio.BCM)
  
trig = 13
echo = 19


def testvalue(a):
    sql = ('select distance from dist order by num desc limit 1')
    cursor.execute(sql)
    rows = cursor.fetchall()

    for row in rows:
      re_dist = row[0]
      diff = re_dist-a

      if diff >= 1:
        print(diff)
        insertfeed(diff)
      else : 
        diff = 0
     
      insertsensor(a)

           

def insertfeed(diff):
   print('insert feed')
   feed = diff*100
   print(feed)
   sql =  """INSERT INTO feed(feed)
           VALUES(%d)""" %feed

   try:
     cursor.execute(sql)
     print("feed db success")
     db.commit()


   except:
     db.rollback()
     print("db Fail")

    


def insertsensor(a):
  sql = """INSERT INTO dist(distance)
           VALUES(%d)""" % a


  try:
    cursor.execute(sql)
    db.commit()

  except:
    db.rollback()
    print("db Fail")


print "start"


pi_time = 0
 
gpio.setup(trig, gpio.OUT)
gpio.setup(echo, gpio.IN)

 

try :
   while True :
     gpio.output(trig, False)
     time.sleep(0.5)
 
     gpio.output(trig, True)
     time.sleep(0.00001)
     gpio.output(trig, False)

     while gpio.input(echo) == 0 :
       pulse_start = time.time()
 
     while gpio.input(echo) == 1 :
       pulse_end = time.time()

     chk_time = int(time.time())
    
#     if(chk_time - pi_time >= 20) :
#        print('chk_time-pi_time')
#        print(chk_time-pi_time)
    
     pulse_duration = pulse_end - pulse_start
     distance = pulse_duration * 17000
     distance = round(distance, 2)
 #       pi_time = chk_time
 
#     print('distance'+distance)

     if distance <=23 :
        if distance >0:
           testvalue(distance)
#    
     time.sleep(0.5)    
 
#     print "Distance : ", distance, "cm"
 
except :
   print('first err')
   gpio.cleanup()

