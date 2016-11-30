package com.example.sunhee.dogcat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Temperature extends AppCompatActivity implements View.OnClickListener{

    private String add = "";
    private int port = 8899;

    TextView now_temp;
    EditText wish_temp;
    Button temp_up, temp_down;
    int temp_count = 0;
    String send_msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        now_temp = (TextView) findViewById(R.id.tv_nowtemp);
        wish_temp = (EditText) findViewById(R.id.et_wishtemp);
        temp_up = (Button) findViewById(R.id.bt_tempup);
        temp_down = (Button) findViewById(R.id.bt_tempdown);

        temp_up.setOnClickListener(this);
        temp_down.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bt_tempup:
                temp_count++;
                break;
            case R.id.bt_tempdown:
                temp_count--;
                break;
        }
        send_msg = "wish/" + temp_count;
        Toast.makeText(Temperature.this, send_msg, Toast.LENGTH_SHORT).show();
        //s_open(send_msg);
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask("192.168.1.99", port, send_message);
        myClientTask.execute();
    }
}
