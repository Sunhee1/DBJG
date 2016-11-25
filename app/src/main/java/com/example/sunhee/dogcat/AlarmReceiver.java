package com.example.sunhee.dogcat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sunhee on 2016-11-23.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm", Toast.LENGTH_LONG).show();
        // 라즈베리파이에 사료 배식하라고 명령 보내기
    }
}
