package com.example.sunhee.dogcat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class reservation_time extends AppCompatActivity implements View.OnClickListener{

    private String add = "";
    private int port = 8888;

    Button reser_register;
    EditText reser_amount;
    TimePicker reser_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_time);

        reser_register = (Button) findViewById(R.id.reser_register);
        reser_amount = (EditText) findViewById(R.id.reser_amount);
        reser_time = (TimePicker) findViewById(R.id.reser_time);

        reser_register.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        int reser_hour = reser_time.getHour();
        int reser_min = reser_time.getMinute();
        String a = reser_amount.getText().toString();
        int amount = 0;

        amount = Integer.parseInt(a);

        Context context = getApplicationContext();
        //s_open("RESER_FEED");
        Toast.makeText(context, reser_hour + " hour, " + reser_min + "min, " + amount + "g", Toast.LENGTH_LONG).show();

        Intent receiverintent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, receiverintent, 0);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR, reser_hour);
        calendar.set(Calendar.MINUTE, reser_min);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, sender);
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}

