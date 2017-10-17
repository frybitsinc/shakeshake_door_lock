package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by spong on 2017-10-17.
 */

public class SettingsActivity extends Activity {
    private Button mButtonPin;
    private Button mButtonPattern;
    private Button mButtonFingerprint;
    private Button mButtonGesture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
//        mButtonPattern.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SettingsPatternActivity.class);
//                startActivity(intent);
//            }
//        });
//        mButtonFingerprint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SettingsFingerprintActivity.class);
//                startActivity(intent);
//            }
//        });
//        mButtonGesture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SettingsGestureActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
