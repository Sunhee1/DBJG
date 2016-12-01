package com.example.sunhee.dogcat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String add = "";
    private int port = 8888;

    SharedPreferences sp;
    Button reser, feed, video, health, info, temp, feedamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getExtras() == null) {
            Intent i = new Intent(getApplicationContext(), Splash.class);
            startActivity(i);
        }

        feed = (Button) findViewById(R.id.bt_feed);
        reser = (Button) findViewById(R.id.bt_reservation);
        video = (Button) findViewById(R.id.bt_video);
        health = (Button) findViewById(R.id.bt_health);
        info = (Button) findViewById(R.id.bt_info);
        temp = (Button) findViewById(R.id.bt_temp);
        feedamount = (Button) findViewById(R.id.bt_amount);

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
        add = "192.168.1.99";

        reser.setOnClickListener(this);
        feed.setOnClickListener(this);
        video.setOnClickListener(this);
        health.setOnClickListener(this);
        info.setOnClickListener(this);
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
                s_open("FEED");
                // 라즈베리파이로 feed 하라고 전송
                break;
            case R.id.bt_video :
                Intent intent2 = new Intent(this, VideoCall.class);
                startActivity(intent2);
                break;
            case R.id.bt_health :
                Intent intent3 = new Intent(this, Health.class);
                startActivity(intent3);
                break;
            case R.id.bt_info :
                //Intent intent4 = new Intent(this, userinfo.class);
                //startActivity(intent4);
                break;
            case R.id.bt_temp :
                Intent intent5 = new Intent(this, Temperature.class);
                startActivity(intent5);
                break;
            case R.id.bt_amount :
                Intent intent6 = new Intent(this, Amount.class);
                startActivity(intent6);
                break;
        }
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}
