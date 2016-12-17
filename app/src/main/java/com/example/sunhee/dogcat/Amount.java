package com.example.sunhee.dogcat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Amount extends AppCompatActivity {

    private String add = "";
    private int port = 8899;
    TextView amount;
    String amount_text;
    int number, percent = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        Intent amount_intent = getIntent();
        number = amount_intent.getIntExtra("re_amount", 0);

        SharedPreferences sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");

        amount = (TextView) findViewById(R.id.tv_amount);

        percent = 100 - (number * 4);
        if(number > 22) percent = 0;
        if(percent < 0) percent = 0;
        amount_text = Integer.toString(percent);
        amount.setText(amount_text + "%");

        if(percent < 25) notice_amount();
    }

    void notice_amount(){
        Context context = getApplicationContext();
        NotificationManager ntmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder ntbuilder = new Notification.Builder(context);

        ntbuilder.setSmallIcon(R.mipmap.ic_launcher);
        ntbuilder.setTicker("돌봐줄개(예약 배식)");
        ntbuilder.setContentTitle("돌봐줄개");
        ntbuilder.setContentText("사료의 양이 얼마 남지 않았습니다.");
        ntbuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        ntbuilder.setAutoCancel(true);

        ntmanager.notify(111, ntbuilder.build());
    }
}
