import socket
from pyfirmata import Arduino, util
import time
import MySQLdb
import pygame

db = MySQLdb.connect(host='192.168.137.94',user='kjh',passwd='1004',db='rpidb')
cursor = db.cursor()

print('db connect')


##sound
pygame.mixer.init()
bang = pygame.mixer.Sound('/home/pi/Downloads/woo.wav')




HOST = ""
PORT = 8888
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('socket created')
s.bind((HOST, PORT))
print(HOST)
print('socket bind complete')
s.listen(1)
print('socket now listening')

board = Arduino('/dev/ttyACM0')

pin_motor = board.get_pin('d:8:o')
it = util.Iterator(board)
it.start()

print('arduino start')   


def updatewish(wish):
    print(wish)
    sql = """update temp set ex_temp=%d order by num desc limit 1""" %wish
    try:
     cursor.execute(sql)
     print('update wish')
     db.commit
    except:
     db.rollback()
     print('db fail')



def do_somestuffs_with_input(input_string):
    split_str = input_string.split("/")
    if split_str[0] == "FEED":
        input_string = "feeding"
        pin_motor.write(False)
        time.sleep(0.25)
        pin_motor.write(True)
        bang.play()
    elif split_str[0] == "wish":
        print('wish')
        input_string = 'wish'
        up_temp = int(split_str[1])
        updatewish(up_temp)     
        input_string = "reservation feeding"
    else:
        input_string = "none"

    return input_string

try:
    while True:
        conn, addr = s.accept()
        print("connected by ", addr)

        data = conn.recv(1024)
        data = data.decode("utf8").strip()
        if not data: break
        print("Received: " + data)

        res = do_somestuffs_with_input(data)
        print("pi play : " + res)

        conn.sendall(res.encode("utf-8"))

        conn.close()

    print("socket close")
    s.close()
except KeyboardInterrupt:
    s.close()
    print("keyboard")

