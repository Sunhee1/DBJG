package com.example.sunhee.dogcat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String add = "";
    private int port = 8888, port2 = 8899;

    int re_temp = 21, re_wishtemp = 23, re_amount = 0, re_sound = 0;
    String re_feed, re_water;
    String[] token;

    SharedPreferences sp;
    ImageButton reser, feed, video, health, info, temp, feedamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.enableDefaults();

        if(getIntent().getExtras() == null) {
            Intent i = new Intent(getApplicationContext(), Splash.class);
            startActivity(i);
        }

        feed = (ImageButton) findViewById(R.id.bt_feed);
        reser = (ImageButton) findViewById(R.id.bt_reservation);
        video = (ImageButton) findViewById(R.id.bt_video);
        health = (ImageButton) findViewById(R.id.bt_health);
        //info = (Button) findViewById(R.id.bt_info);
        temp = (ImageButton) findViewById(R.id.bt_temp);
        feedamount = (ImageButton) findViewById(R.id.bt_amount);

        sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if(!hasVisited){ // application 최초 설치 시에만 device와의 연결을 위해 ip 등록
            Intent i2 = new Intent(getApplicationContext(), Register.class);
            startActivity(i2);
            finish();
            SharedPreferences.Editor sp_editor = sp.edit();
            sp_editor.putBoolean("hasVisited", true);
            sp_editor.commit();
        }

        add = sp.getString("IP", "");

        //ConnectThread c_thread = new ConnectThread();
        //c_thread.set_ip(add);
        //c_thread.run();

        reser.setOnClickListener(this);
        feed.setOnClickListener(this);
        video.setOnClickListener(this);
        health.setOnClickListener(this);
//      info.setOnClickListener(this);
        temp.setOnClickListener(this);
        feedamount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.bt_reservation :
                Intent intent = new Intent(this, reservation_time.class);
                startActivity(intent);
                break;
            case R.id.bt_feed :
                s_open("FEED", port);
                break;
            case R.id.bt_video :
                Intent intent2 = new Intent(this, VideoCall.class);
                startActivity(intent2);
                break;
            case R.id.bt_health :
                Intent intent3 = new Intent(this, Health.class);
                startActivity(intent3);
                break;
            //case R.id.bt_info :
                //Intent intent4 = new Intent(this, userinfo.class);
                //startActivity(intent4);
              //  break;
            case R.id.bt_temp :
                Intent intent5 = new Intent(this, Temperature.class);
                intent5.putExtra("re_temp", re_temp);
                intent5.putExtra("re_wish", re_wishtemp);
                startActivity(intent5);
                break;
            case R.id.bt_amount :
                Intent intent6 = new Intent(this, Amount.class);
                intent6.putExtra("re_amount", re_amount);
                startActivity(intent6);
                break;
        }
    }

    void s_open(String send_message, int PORT)
    {
        if(PORT == port) {
            ClientTask myClientTask = new ClientTask(add, PORT, send_message);
            myClientTask.execute();
        }else{
            DBTask dbTask = new DBTask(add, PORT, send_message);
            dbTask.execute();
        }
    }

    class ConnectThread extends Thread {

        public void run() {
            connect();
        }

        private void connect() {
            s_open("temp", port2);
            s_open("amount", port2);
            s_open("sound", port2);
            s_open("week_feed", port2);
            s_open("week_water", port2);
        }
    }


    public class DBTask extends AsyncTask<Void, Void, Void> {
        int s_port;
        String s_add, re_msg = "", send_msg = "";

        DBTask(String addr, int port, String message){
            s_add = addr;
            s_port = port;
            send_msg = message;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Socket socket = null;
            send_msg = send_msg.toString();
            try {
                socket = new Socket(s_add, s_port);

                //송신
                OutputStream out = socket.getOutputStream();
                out.write(send_msg.getBytes());

                //수신
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];
                int bytesRead;
                InputStream inputStream = socket.getInputStream();

                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    re_msg += byteArrayOutputStream.toString("UTF-8");
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
                re_msg = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                re_msg = "IOException: " + e.toString();
            }finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(send_msg.equals("temp")){
                token = re_msg.split("\\/");
                re_temp = Integer.parseInt(token[0]);
                re_wishtemp = Integer.parseInt(token[1]);
            }
            else if(send_msg.equals("amount")) re_amount = Integer.parseInt(re_msg);
            else if(send_msg.equals("sound")) re_sound = Integer.parseInt(re_msg);
            else if(send_msg.equals("week_feed")) re_feed = re_msg;
            else if(send_msg.equals("week_water")) re_water = re_msg;
        }
    }
}
