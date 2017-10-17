package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button mButtonSettings;
    private Button mButtonUnlock;
    private Button mButtonLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonSettings = (Button) findViewById(R.id.button_settings);
        mButtonUnlock = (Button) findViewById(R.id.button_unlock);
        mButtonLock = (Button) findViewById(R.id.button_lock);

        mButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        mButtonUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UnlockActivity.class);
                startActivity(intent);
            }
        });
        mButtonLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LockActivity.class);
                startActivity(intent);
            }
        });
    }
}
