package com.example.sunhee.dogcat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sunhee on 2016-11-23.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private String add = "192.168.1.99";
    private int port = 8888;

    private NotificationManager ntmanager;
    private Notification.Builder ntbuilder;

    int amount = 100;

    AlarmReceiver(String re_add, int re_amount){
        add = re_add;
        amount = re_amount;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        ntmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        ntbuilder = new Notification.Builder(context);

        ntbuilder.setSmallIcon(R.mipmap.ic_launcher);
        ntbuilder.setTicker("돌봐줄개(예약 배식)");
        ntbuilder.setContentTitle("돌봐줄개");
        ntbuilder.setContentText("예약된 시간에 자동 배식이 되었습니다.");
        ntbuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        ntbuilder.setAutoCancel(true);

        ntmanager.notify(111, ntbuilder.build());

        int count = (amount / 100) + 1;
        if(amount % 100 == 0) count--;

        for(int i = 0; i < count; i++) s_open("FEED");

        Toast.makeText(context, "예약 배식이 되었습니다.", Toast.LENGTH_LONG).show();
        // 라즈베리파이에 사료 배식하라고 명령 보내기
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}
