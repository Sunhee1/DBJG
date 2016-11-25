package com.example.sunhee.dogcat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String add = "";
    private int port = 8888;

    SharedPreferences sp;
    Button reser, feed, video;
    TextView recieveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getExtras() == null) {
            Intent i = new Intent(getApplicationContext(), Splash.class);
            startActivity(i);
        }

        recieveText = (TextView) findViewById(R.id.recieveText);
        feed = (Button) findViewById(R.id.bt_feed);
        reser = (Button) findViewById(R.id.bt_reservation);
        video = (Button) findViewById(R.id.bt_video);


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

        reser.setOnClickListener(this);
        feed.setOnClickListener(this);
        video.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.bt_reservation :
                Intent intent = new Intent(this, Reservation.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_feed :
                s_open("FEED");
                // 라즈베리파이로 feed 하라고 전송
                break;
            case R.id.bt_video :
                Intent intent2 = new Intent(this, VideoCall.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}
