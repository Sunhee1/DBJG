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
    Button temp_up, temp_down, temp_ok;
    int now_temperature, wish_temperature, temp_count, count = 0;
    String temp_str, send_msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        now_temp = (TextView) findViewById(R.id.tv_nowtemp);
        wish_temp = (EditText) findViewById(R.id.et_wishtemp);
        temp_up = (Button) findViewById(R.id.bt_tempup);
        temp_down = (Button) findViewById(R.id.bt_tempdown);
        temp_ok = (Button) findViewById(R.id.bt_tempok);

        temp_up.setOnClickListener(this);
        temp_down.setOnClickListener(this);
        temp_ok.setOnClickListener(this);

        now_temperature = 21;
        wish_temperature = 23;
        temp_str = "" + now_temperature;
        now_temp.setText(temp_str);

        //s_open("wish_temp");
        temp_str = "" + wish_temperature;
        wish_temp.setText(temp_str);

        SharedPreferences sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");
        add = "192.168.1.99";
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bt_tempup:
                if(count < 9) {
                    count++;
                    wish_temperature = wish_temperature + count;
                    temp_str = "" + wish_temperature;
                    wish_temp.setText(temp_str);
                }else{
                    send_msg = "Hot!";
                    Toast.makeText(Temperature.this, send_msg, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_tempdown:
                if(count > -9) {
                    count--;
                    wish_temperature = wish_temperature + count;
                    temp_str = "" + wish_temperature;
                    wish_temp.setText(temp_str);
                }else{
                    send_msg = "Cold!";
                    Toast.makeText(Temperature.this, send_msg, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_tempok:
                temp_count = Integer.parseInt(wish_temp.getText().toString());
                send_msg = "wish/" + temp_count;
                Toast.makeText(Temperature.this, send_msg, Toast.LENGTH_SHORT).show();
                s_open(send_msg);
                finish();
                break;
        }
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask("192.168.1.99", port, send_message);
        myClientTask.execute();
    }
}
