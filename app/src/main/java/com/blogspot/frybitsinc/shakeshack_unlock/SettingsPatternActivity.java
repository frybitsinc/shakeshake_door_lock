package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amnix.materiallockview.MaterialLockView;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static com.blogspot.frybitsinc.shakeshack_unlock.R.id.pattern;

/**
 * Created by spong on 2017-10-18.
 */

public class SettingsPatternActivity extends Activity {
    private Button mButtonConfirm;
    private MaterialLockView materialLockView;
    private String pattern_input, confirm_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_pattern);
        SharedPreference.init(this);
        materialLockView = (MaterialLockView) findViewById(pattern);
        materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
            @Override
            public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                super.onPatternDetected(pattern, SimplePattern);
                Log.d("SettingsPatternActivity", "onPatternDetected: SimplePattern = "+SimplePattern);
                pattern_input = SimplePattern;
            }
        });
        mButtonConfirm = (Button) findViewById(R.id.button_confirm);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_input = pattern_input;
                //잠금 패턴 설정
                SharedPreference.setString(SharedPreference.UNLOCK_MODE, SharedPreference.PATTERN);
                SharedPreference.setString(SharedPreference.PATTERN, confirm_input);
                //print
//                    Toast.makeText(getApplicationContext(), SharedPreference.getString(UNLOCK_MODE), LENGTH_LONG).show();
                String currentUnlockMode = SharedPreference.getString(SharedPreference.UNLOCK_MODE);
                if(currentUnlockMode==null){
                    Log.d("UNLOCK_MODE",  "null");
                }
                else{
                    Log.d("UNLOCK_MODE", currentUnlockMode);
                }
                String currentPattern = SharedPreference.getString(SharedPreference.PATTERN);
                if(currentUnlockMode==null){
                    Log.d("UNLOCK_PATTERN",  "null");
                }
                else{
                    Log.d("UNLOCK_PATTERN", currentPattern);
                }
                //확인 toast
                Toast.makeText(getApplicationContext(), "PATTERN setting done !", LENGTH_LONG).show();
                //뒤로
                finish();
            }
        });
    }
}
