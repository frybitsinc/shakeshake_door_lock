package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by spong on 2017-10-17.
 */

public class UnlockPinActivity extends Activity {
    private Button mButtonConfirm;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_pin);
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
                }
                else{
                    //4자리맞음
                    //잠금 핀 확인
                    if(pin_input.equals(SharedPreference.getString(SharedPreference.PIN))){
                        Toast.makeText(getApplicationContext(), "PIN is correct", LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "PIN is wrong....check again please!", LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
