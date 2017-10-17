package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by spong on 2017-10-18.
 */

public class SettingsFingerprintActivity extends Activity {
    private Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_fingerprint);
        SharedPreference.init(this);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
//                startActivity(intent);
                //L 이하, 지문인식 하드웨어 미지원
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Toast.makeText(getApplicationContext(), "Android version of your device is not supported...", LENGTH_LONG).show();
                }
                //M 이상, 지문인식 하드웨어 지원
                else {
                    FingerprintManager fingerprintManager = (FingerprintManager) getApplicationContext().getSystemService(FINGERPRINT_SERVICE);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        Log.d("SettingsFinger", "initViews: use fingerprint permission granted");
                    }
                    if (!fingerprintManager.isHardwareDetected()) {
                        // Device doesn't support fingerprint authentication
                        Log.d("SettingsFinger", "initViews: isHardwareDetected = NO");
                        Toast.makeText(getApplicationContext(), "Hardware of your device is not supported...", LENGTH_LONG).show();
                    } else {
                        Log.d("SettingsFinger", "initViews: isHardwareDetected = YES");
                        //supportable
                        Intent register_intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                        register_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(register_intent);
                    }
                }
            }
        });
    }
}
