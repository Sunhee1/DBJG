package com.example.sunhee.dogcat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Health extends AppCompatActivity {

    String[] horlabels, verlabels, data_feed, data_water;
    String r_feed, r_water;
    float[] goals, values;
    GraphView graphview;
    LinearLayout graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        Intent re_intent = getIntent();
        r_feed = re_intent.getStringExtra("re_feed");
        r_water = re_intent.getStringExtra("re_water");

        SharedPreferences sp = getSharedPreferences("dogcat", MODE_PRIVATE);

        values = new float[]{0f, 0, 0, 0, 0, 0, 0, 0, 0.0f};
        goals = new float[]{0f, 0, 0, 0, 0, 0, 0, 0, 0.0f};

        data_feed = r_feed.split("\\/");
        data_water = r_water.split("\\/");

        for(int i = 0; i < 7; i++){
            values[i] = Float.parseFloat(data_feed[i]);
            goals[i] = Float.parseFloat(data_water[i]);
        }

        verlabels = new String[]{"1100", "900", "700", "500", "300", "100", "0 (g)"};
        horlabels = new String[]{"", "1(Today)", "2", "3", "4", "5", "6","7"};

        graphview = new GraphView(this, values, goals, "반려동물의 사료 및 물 섭취량", horlabels, verlabels, GraphView.BAR);
        graph = (LinearLayout) findViewById(R.id.graph);
        graph.addView(graphview);
    }
}
