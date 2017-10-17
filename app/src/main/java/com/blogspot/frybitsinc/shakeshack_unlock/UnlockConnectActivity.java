package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by spong on 2017-10-17.
 */

public class UnlockConnectActivity extends Activity {
    private Button btn;
    private TextView textView;
    private EditText editText;
    private Button btn_send;
    private Socket client;
    private String ip = "192.168.123.104";
    private int port = 50100;
    private Thread thread;
    ClientThread clientThread;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_connect);

        btn = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        btn_send = (Button) findViewById(R.id.btn_send);
//        btn_recv = (Button) findViewById(R.id.btn_recv);
        handler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                Log.d("UnlockConnectActivity", "handleMessage: "+bundle.getString("msg"));
                textView.append(bundle.getString("msg")+"\n");
            }
        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientThread.send(editText.getText().toString());
                editText.setText("");
            }
        });
    }
    public void connect(){
        Log.d("UnlockConnectActivity", "connect() : called");
        thread = new Thread(){
            public void run() {
                super.run();
                try {
                    client = new Socket(ip, port);
                    clientThread = new ClientThread(client, handler);
                    clientThread.start();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
