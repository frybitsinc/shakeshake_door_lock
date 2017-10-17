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

public class SettingsPinActivity extends Activity {
    private Button mButtonConfirm;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_pin);
        mButtonConfirm = (Button) findViewById(R.id.button_confirm);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SettingsPinActivity.class);
//                startActivity(intent);
                editText = (EditText)findViewById(R.id.editText_pin);
                String pin_input = editText.getText().toString();
                Toast.makeText(getApplicationContext(), pin_input, LENGTH_LONG).show();
                //4자리수체크
                if(pin_input.length()!=4){
                    //4자리아님
                    Toast.makeText(getApplicationContext(), "Please enter 4 digits...!", LENGTH_LONG).show();
                    //edittext 초기화 >다시입력focus
                }
                else{
                    Toast.makeText(getApplicationContext(), "PIN setting done !", LENGTH_LONG).show();
                    //잠금 핀 설정
                    //toast
                    //메인 ㄱㄱ
                }
            }
        });
    }
}
