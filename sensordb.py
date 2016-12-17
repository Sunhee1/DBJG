import MySQLdb
import socket

#db = MySQLdb.connect(host='192.168.137.94',user='kjh',passwd='1004',db='rpidb')
#cursor = db.cursor()


HOST = ""
PORT = 8899
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('socket created')
s.bind((HOST, PORT))
print(HOST)
print('socket bind complete')
s.listen(1)
print('socket now listening')


def updatewish(wish):
    print(wish)
    sql = "update temp set ex_temp= %d order by num desc limit 1"  % (wish)
    try:
        cursor.execute(sql)
        print('update wish')
        db.commit()
    except:
        db.rollback()
        print('db fail')


def select(input_data):
    if input_data == "temp" :
        sql = 'select r_temp,ex_temp from temp order by num desc limit 1'
        cursor.execute(sql)
        rows = cursor.fetchall()

        for row in rows:
            a = row[0]
            b = row[1]
            s = "%d/%d" %(a,b)
            print(s)
    elif input_data == "amount" :
        sql =  'select distance from dist order by num desc limit 1'
        cursor.execute(sql)
        rows = cursor.fetchall()

        for row in rows:
            c = row[0]
            s = "%d" % c
            print(s)
    elif input_data == "sound" :
        sql = 'select sound from sound order by num desc limit 1'
        cursor.execute(sql)
        rows = cursor.fetchall()

        for row in rows:
            d = row[0]
            s = "%d" % d
            print(s)
            
    elif input_data == "week_feed" :
        sql = 'select feed from week_feed'
        cursor.execute(sql)
        rows = cursor.fetchall()

        we_feed = [1, 2, 3, 4, 5, 6, 7]
        feed_cnt = 0
        for row in rows:
            we_feed[feed_cnt] = row[0]
            feed_cnt = feed_cnt + 1

        s = "%d/%d/%d/%d/%d/%d/%d" % (we_feed[0], we_feed[1], we_feed[2], we_feed[3], we_feed[4], we_feed[5], we_feed[6])

        print(s)
            
    elif input_data == "week_water" :
        sql = 'select water from week_water'
        cursor.execute(sql)
        rows = cursor.fetchall()

        we_water = [1, 2, 3, 4, 5, 6, 7]
        water_cnt = 0
        for row in rows:
            we_water[water_cnt] = row[0]
            water_cnt = water_cnt + 1

        s = "%d/%d/%d/%d/%d/%d/%d" % (we_water[0], we_water[1], we_water[2], we_water[3], we_water[4], we_water[5], we_water[6])

        print(s)

    return s


def do_somestuffs_with_input(input_string):
    split_str = input_string.split("/")
    if split_str[0] == "temp":
        print('temp')
        input_string = select("temp")
    elif split_str[0] == "amount":
        print('amount')
        input_string = select("amount")
    elif split_str[0] == "sound" :
        print('sound')
        input_string = select("sound")
    elif split_str[0] == "week_feed" :
        print('week_feed')
        input_string = select("week_feed")
    elif split_str[0] == "week_water" :
        print('week_water')
        input_string = select("week_water")
    elif split_str[0] == "wish":
        print('wish')
        input_string = 'wish'
        up_temp = int(split_str[1])
        updatewish(up_temp)
        input_string = "reservation feeding"
    else:
        input_string = input_string + " NO"

    return input_string


try:
    while True:
        db = MySQLdb.connect(host='192.168.137.94',user='kjh',passwd='1004',db='rpidb')
	cursor = db.cursor()
        print('db connect')
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

