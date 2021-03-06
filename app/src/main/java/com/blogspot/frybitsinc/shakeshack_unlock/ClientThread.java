package com.blogspot.frybitsinc.shakeshack_unlock;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by spong on 2017-10-17.
 */

public class ClientThread  extends Thread {
    BufferedReader bufferR;
    BufferedWriter bufferW;
    Socket client;
    Handler handler;

    public ClientThread(Socket client, Handler handler) {
        this.handler = handler;
        try {
            this.client = client;
            //connect socket get stream
            bufferR = new BufferedReader(new InputStreamReader(client.getInputStream()));
            bufferW = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //send
    public void send(String data){
        System.out.println("send");
        try {
            System.out.println("data:"+data);
            bufferW.write(data);
            bufferW.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //listen
    public String listen(){
        String msg=null;
        try {
            Log.d("ClientThread", "listen: try while");
            while(true){
                Log.d("ClientThread", "listen: while");
                //readLine() caused problem... need to append "/n" when server sends string
                msg=bufferR.readLine();
                Message m = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", msg);
                m.setData(bundle);
                handler.sendMessage(m);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        super.run();
        listen();
    }
}
