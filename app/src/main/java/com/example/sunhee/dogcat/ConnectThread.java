package com.example.sunhee.dogcat;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by sunhee on 2016-12-01.
 */

public class ConnectThread extends Thread {

    public void run() {
        connect();
    }

    private void connect() {

    }
}
