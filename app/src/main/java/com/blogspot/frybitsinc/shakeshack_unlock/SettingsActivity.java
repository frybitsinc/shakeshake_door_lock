package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by spong on 2017-10-17.
 */

public class SettingsActivity extends Activity {
    private TextView mTextviewCurrentLock;
    private Button mButtonPin;
    private Button mButtonPattern;
    private Button mButtonFingerprint;
    private Button mButtonGesture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //current lock mode
        SharedPreference.init(this);
        String currentUnlockMode = SharedPreference.getString(SharedPreference.UNLOCK_MODE);
        mTextviewCurrentLock = (TextView) findViewById(R.id.text_current_lock);
        mTextviewCurrentLock.setText(currentUnlockMode);
        //button
        mButtonPin = (Button) findViewById(R.id.button_pin);
        mButtonPattern = (Button) findViewById(R.id.button_pattern);
        mButtonFingerprint = (Button) findViewById(R.id.button_fingerprint);
        mButtonGesture = (Button) findViewById(R.id.button_gesture);

        mButtonPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsPinActivity.class);
                startActivity(intent);
            }
        });
        mButtonPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsPatternActivity.class);
                startActivity(intent);
            }
        });
        mButtonFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsFingerprintActivity.class);
                startActivity(intent);
            }
        });
        mButtonGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), UnlockActivity.class);
                Intent intent = new Intent(getApplicationContext(), SettingsGestureActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //current lock mode REFRESH!
        SharedPreference.init(this);
        String currentUnlockMode = SharedPreference.getString(SharedPreference.UNLOCK_MODE);
        mTextviewCurrentLock = (TextView) findViewById(R.id.text_current_lock);
        mTextviewCurrentLock.setText(currentUnlockMode);
    }
}
