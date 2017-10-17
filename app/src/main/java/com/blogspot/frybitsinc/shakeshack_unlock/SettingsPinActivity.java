package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by spong on 2017-10-17.
 */

public class SettingsPinActivity extends Activity {
    private Button mButtonConfirm;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_pin);
        SharedPreference.init(this);
        mButtonConfirm = (Button) findViewById(R.id.button_confirm);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = (EditText)findViewById(R.id.editText_pin);
                String pin_input = editText.getText().toString();
//                Toast.makeText(getApplicationContext(), pin_input, LENGTH_LONG).show();
                //4자리수체크
                if(pin_input.length()!=4){
                    //4자리아님
                    Toast.makeText(getApplicationContext(), "Please enter 4 digits...!", LENGTH_LONG).show();
                    //edittext 초기화 >다시입력focus
                }
                else{
                    //잠금 핀 설정
                    SharedPreference.setString(SharedPreference.UNLOCK_MODE, SharedPreference.PIN);
                    SharedPreference.setString(SharedPreference.PIN, pin_input);
                    //print
//                    Toast.makeText(getApplicationContext(), SharedPreference.getString(UNLOCK_MODE), LENGTH_LONG).show();
                    String currentUnlockMode = SharedPreference.getString(SharedPreference.UNLOCK_MODE);
                    if(currentUnlockMode==null){
                        Log.d("UNLOCK_MODE",  "null");
                    }
                    else{
                        Log.d("UNLOCK_MODE", currentUnlockMode);
                    }
                    String currentPin = SharedPreference.getString(SharedPreference.PIN);
                    if(currentUnlockMode==null){
                        Log.d("UNLOCK_PIN",  "null");
                    }
                    else{
                        Log.d("UNLOCK_PIN", currentPin);
                    }
                    //확인 toast
                    Toast.makeText(getApplicationContext(), "PIN setting done !", LENGTH_LONG).show();
                    //뒤로
                    finish();
                }
            }
        });
    }
}
