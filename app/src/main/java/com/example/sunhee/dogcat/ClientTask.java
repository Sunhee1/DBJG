package com.example.sunhee.dogcat;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by sunhee on 2016-11-24.
 */

public class ClientTask extends AsyncTask<Void, Void, Void> {
    String s_add;
    int s_port;
    String re_msg = "rere";
    String send_msg = "";

    ClientTask(String addr, int port, String message){
        s_add = addr;
        s_port = port;
        send_msg = message;
    }

    public String getRe_msg(){
        return re_msg;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;
        send_msg = send_msg.toString();
        try {
            socket = new Socket(s_add, s_port);

            //송신
            OutputStream out = socket.getOutputStream();
            out.write(send_msg.getBytes());

            //수신
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            while ((bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                re_msg += byteArrayOutputStream.toString("UTF-8");
            }
            re_msg = "receive msg :  " + re_msg;


        } catch (UnknownHostException e) {
            e.printStackTrace();
            re_msg = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            re_msg = "IOException: " + e.toString();
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
