package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by spong on 2017-09-07.
 */

public class LockActivity extends Activity {
    private Button mButtonLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        mButtonLock = (Button) findViewById(R.id.button_lock);
        mButtonLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Already locked...!", LENGTH_LONG).show();
            }
        });
    }
}
