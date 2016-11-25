package com.example.sunhee.dogcat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class VideoCall extends AppCompatActivity implements View.OnClickListener{

    private String add = "";
    private int port = 8888;

    SharedPreferences sp;

    WebView wv;
    WebSettings ws;
    Button videofeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        wv = (WebView) findViewById(R.id.pi_video);
        wv.setWebViewClient(new WebViewClient());
        wv.setBackgroundColor(255);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);

        ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);

        wv.loadUrl("http://192.168.0.17:8080/stream/video.mjpeg");

        sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");

        videofeed = (Button) findViewById(R.id.bt_videofeed);
        videofeed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bt_videofeed:
                s_open("FEED");
                break;
        }
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}
