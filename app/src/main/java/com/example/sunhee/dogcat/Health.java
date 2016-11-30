package com.example.sunhee.dogcat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Health extends AppCompatActivity {

    private String add = "";
    private int port = 8899;

    String[] horlabels, verlabels;
    float[] goals, values;
    GraphView graphview;
    LinearLayout graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        SharedPreferences sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");

        s_open("week_feed");

        values = new float[]{100.0f, (float) 44.4, 33, 55, 60, 70, 24, 30, 60, 45, 32, 50, 60, 70, 80, 90, 0.0f};
        goals = new float[]{100.0f, 60, 40, 55, 80, 90, 50, 20, 80, 50, 40, 60, 70, 80, 90, 100, 0.0f};

        verlabels = new String[]{"10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0 (h)"};
        horlabels = new String[]{"", "1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12", "13", "14", "15"};

        graphview = new GraphView(this, values, goals, "반려동물의 사료 및 물 섭취량", horlabels, verlabels, GraphView.BAR);
        graph = (LinearLayout) findViewById(R.id.graph);
        graph.addView(graphview);
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}
