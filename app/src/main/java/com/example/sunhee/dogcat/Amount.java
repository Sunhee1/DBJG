package com.example.sunhee.dogcat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Amount extends AppCompatActivity {

    private String add = "";
    private int port = 8899;

    String[] horlabels, verlabels;
    float[] goals, values;
    GraphView graphview;
    LinearLayout graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        SharedPreferences sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");

        s_open("now_feed");

        values = new float[]{100.0f, 23, 0.0f};
        goals = new float[]{100.0f, 13, 0.0f};

        verlabels = new String[]{"100", "80", "60", "40", "20", "0 (%)"};
        horlabels = new String[]{"", "1"};

        graphview = new GraphView(this, values, goals, "사료통의 남은 사료량", horlabels, verlabels, GraphView.BAR);
        graph = (LinearLayout) findViewById(R.id.graph2);
        graph.addView(graphview);
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask("192.168.1.99", port, send_message);
        myClientTask.execute();
    }
}
