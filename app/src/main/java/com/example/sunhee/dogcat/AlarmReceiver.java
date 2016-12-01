package com.example.sunhee.dogcat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sunhee on 2016-11-23.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private String add = "192.168.1.99";
    private int port = 8888;

    private NotificationManager ntmanager;
    private Notification.Builder ntbuilder;

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

        s_open("FEED");

        Toast.makeText(context, "alarm", Toast.LENGTH_LONG).show();
        // 라즈베리파이에 사료 배식하라고 명령 보내기
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}
