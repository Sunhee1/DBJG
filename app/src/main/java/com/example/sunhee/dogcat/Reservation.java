package com.example.sunhee.dogcat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Reservation extends AppCompatActivity implements View.OnClickListener {

    Button button_setAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        button_setAlarm = (Button) findViewById(R.id.go_register);
        button_setAlarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, reservation_time.class);
        startActivity(i);
        finish();
    }
}
