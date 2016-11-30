package com.example.sunhee.dogcat;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class VideoCall extends AppCompatActivity implements View.OnClickListener{

    private String add = "";
    private int port = 8888;

    SharedPreferences sp;

    WebView wv;
    WebSettings ws;
    Button videofeed, videocapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        sp = getSharedPreferences("dogcat", MODE_PRIVATE);
        add = sp.getString("IP", "");

        String video_url = "";

        video_url = "http://" + add + ":8080/stream/video.mjpeg";

        wv = (WebView) findViewById(R.id.pi_video);
        wv.setWebViewClient(new WebViewClient());
        wv.setBackgroundColor(255);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);

        ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);

        wv.loadUrl(video_url);

        videofeed = (Button) findViewById(R.id.bt_videofeed);
        videocapture = (Button) findViewById(R.id.bt_capture);

        videofeed.setOnClickListener(this);
        videocapture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bt_videofeed:
                s_open("FEED");
                break;
            case R.id.bt_capture :
                screenshot(v);
                break;
        }
    }

    public void screenshot(View view){
        Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
        view.setDrawingCacheEnabled(true);
        Bitmap screentshot = view.getDrawingCache();

        Calendar date;
        int year, month, day, hour, min;

        year = month = day = hour = min = 0;

        date = Calendar.getInstance();
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH) + 1;
        day = date.get(Calendar.DAY_OF_MONTH);
        hour = date.get(Calendar.HOUR);
        min = date.get(Calendar.MINUTE);

        String filename = "screenshot/" + year + "_" + month + "_" + day + "/" + hour + ":" + min + ".png";

        if(screentshot != null) {
            try {
                Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
                File f = new File(Environment.getDataDirectory(), filename);
                f.createNewFile();

                OutputStream outStream = new FileOutputStream(f);
                screentshot.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.close();

            } catch (IOException e) {
                Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        view.setDrawingCacheEnabled(false);
    }

    void s_open(String send_message)
    {
        ClientTask myClientTask = new ClientTask(add, port, send_message);
        myClientTask.execute();
    }
}
