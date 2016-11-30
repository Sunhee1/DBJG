package com.example.sunhee.dogcat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        SharedPreferences sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");

        amount = (TextView) findViewById(R.id.tv_amount);

        s_open("now_feed");

        amount_text = "70%";
        amount.setText(amount_text);
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask("192.168.1.99", port, send_message);
        myClientTask.execute();
    }
}
