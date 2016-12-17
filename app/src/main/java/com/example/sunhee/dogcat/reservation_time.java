package com.example.sunhee.dogcat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class reservation_time extends AppCompatActivity implements View.OnClickListener{

    private String add = "";
    private int port = 8888;

    ImageButton reser_register;
    EditText reser_amount;
    TimePicker reser_time;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_time);

        reser_register = (ImageButton) findViewById(R.id.reser_register);
        reser_amount = (EditText) findViewById(R.id.reser_amount);
        reser_time = (TimePicker) findViewById(R.id.reser_time);

        reser_register.setOnClickListener(this);

        sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");

        Intent iinn = new Intent(this, AlarmReceiver.class);
        PendingIntent op1 = PendingIntent.getBroadcast(this, 0, iinn, PendingIntent.FLAG_NO_CREATE);

        if(op1 == null){
            Toast.makeText(reservation_time.this, "예약 없음.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(reservation_time.this, "예약 있음.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        int reser_hour = reser_time.getHour();
        int reser_min = reser_time.getMinute();
        String a = reser_amount.getText().toString();
        int amount = 0;
        Context context = getApplicationContext();

        amount = Integer.parseInt(a);

        if(amount < 0 || amount > 1100){
            Toast.makeText(context, "1 - 1100 사이의 값을 입력해 주세요.", Toast.LENGTH_LONG).show();
        }else {
            sp = getSharedPreferences("dogcat", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            AlarmReceiver ar = new AlarmReceiver(add, amount);
            editor.putInt("AMOUNT", amount);
            editor.commit();

            Intent receiverintent = new Intent(this, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(this, 0, receiverintent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, reser_hour);
            calendar.set(Calendar.MINUTE, reser_min);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, sender);

            Toast.makeText(context, reser_hour + "시 " + reser_min + "분 예약 완료.", Toast.LENGTH_LONG).show();

            finish();
        }
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}

