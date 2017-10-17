package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amnix.materiallockview.MaterialLockView;

import java.util.List;

/**
 * Created by spong on 2017-10-18.
 */

public class SettingsPatternActivity extends Activity {
    private Button mButtonConfirm;
    private MaterialLockView materialLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_pattern);
        SharedPreference.init(this);
        materialLockView = (MaterialLockView) findViewById(R.id.pattern);
        materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
            @Override
            public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                super.onPatternDetected(pattern, SimplePattern);
            }
        });
        mButtonConfirm = (Button) findViewById(R.id.button_confirm);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), LockActivity.class);
//                startActivity(intent);
            }
        });
    }
}
