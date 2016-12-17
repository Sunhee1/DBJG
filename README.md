# DBJG
SW 캡스톤 디자인 (돌봐줄개)

------ 라즈베리파이 명령어 ------

Tftlcd 온도 띄워주기
-> sudo python temp.py


수동 모터 스위치 & 소리 센서 실행
-> sudo modprobe snd-bcm2835
-> sudo amixer cset numid=3 1
-> sudo python sound_motor.py


센서 값 DB 스레드 실행
-> sudo python sensordb.py


안드로이드 소켓 통신 실행
-> sudo python and_socket.py


카메라 실행
-> sudo raspi-config 
-> Enable camera 셋팅

-> sudo modprobe bcm2835-v4l2
-> sudo service uv4l_raspicam restart


거리 센서 측정 
-> sudo python pidistance.py


라즈베리파이 MySQL
-> mysql -u root -p 접속
-> use rpidb;
