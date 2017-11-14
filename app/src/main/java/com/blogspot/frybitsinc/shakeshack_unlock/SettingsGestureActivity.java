package com.blogspot.frybitsinc.shakeshack_unlock;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by spong on 2017-11-14.
 */

public class SettingsGestureActivity extends AppCompatActivity {

    /*Wizets*/
    private TextView tv_roll, tv_pitch, tv_yaw;
    private Button btn_on_off, btn_pitch_off, btn_roll_off, btn_yaw_off, btn_set;
    private NumberPicker num_pitch, num_roll, num_yaw;

    /*Used for Accelometer & Gyroscoper*/
    private SensorManager mSensorManager = null;
    private UserSensorListner userSensorListner;
    private Sensor mGyroscopeSensor = null;
    private Sensor mAccelerometer = null;

    /*Sensor variables*/
    private float[] mGyroValues = new float[3];
    private float[] mAccValues = new float[3];
    private double mAccRoll, mAccPitch, mAccYaw;

    /*for unsing complementary fliter*/
    private float a = 0.2f;
    private static final float NS2S = 1.0f/1000000000.0f;
    private double pitch = 0, roll = 0, yaw = 0;
    private double timestamp;
    private double dt;
    private double temp;
    private boolean running;
    private boolean gyroRunning;
    private boolean accRunning;
    private boolean pitch_val_change, roll_val_change, yaw_val_change;
    private String gesture_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_gesture);

        tv_pitch = (TextView) findViewById(R.id.tv_pitch);
        tv_roll = (TextView) findViewById(R.id.tv_roll);
        tv_yaw = (TextView) findViewById(R.id.tv_yaw);

        btn_pitch_off = (Button) findViewById(R.id.pitch_on);
        btn_roll_off = (Button) findViewById(R.id.roll_on);
        btn_yaw_off = (Button) findViewById(R.id.yaw_on);
        pitch_val_change = true;
        roll_val_change = true;
        yaw_val_change = true;
        btn_on_off = (Button) findViewById(R.id.filter);
        btn_set = (Button) findViewById(R.id.set_pw);

        num_pitch = (NumberPicker) findViewById(R.id.numberPicker_pitch);
        num_roll = (NumberPicker) findViewById(R.id.numberPicker_roll);
        num_yaw = (NumberPicker) findViewById(R.id.numberPicker_yaw);
        num_pitch.setMinValue(0);
        num_pitch.setMaxValue(9);
        num_roll.setMinValue(0);
        num_roll.setMaxValue(9);
        num_yaw.setMinValue(0);
        num_yaw.setMaxValue(9);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        userSensorListner = new UserSensorListner();
        mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mAccelerometer= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        btn_pitch_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pitch_val_change){
                    pitch_val_change = false;
                    btn_pitch_off.setText("on");
                }
                else{
                    pitch_val_change = true;
                    btn_pitch_off.setText("off");
                }
            }
        });
        btn_roll_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roll_val_change){
                    roll_val_change = false;
                    btn_roll_off.setText("on");
                }
                else{
                    roll_val_change = true;
                    btn_roll_off.setText("off");
                }
            }
        });
        btn_yaw_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yaw_val_change){
                    yaw_val_change = false;
                    btn_yaw_off.setText("on");
                }
                else{
                    yaw_val_change = true;
                    btn_yaw_off.setText("off");
                }
            }
        });
        btn_on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* 실행 중이지 않을 때 -> 실행 */
                if(!running){
                    running = true;
                    btn_on_off.setText("SENSOR OFF");
                    mSensorManager.registerListener(userSensorListner, mGyroscopeSensor, SensorManager.SENSOR_DELAY_UI);
                    mSensorManager.registerListener(userSensorListner, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
                }

                /* 실행 중일 때 -> 중지 */
                else if(running) {
                    running = false;
                    btn_on_off.setText("SENSOR ON");
                    mSensorManager.unregisterListener(userSensorListner);

                }
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set password
                gesture_input = String.valueOf(num_pitch.getValue()) + String.valueOf(num_roll.getValue()) + String.valueOf(num_yaw.getValue());
                //잠금 핀 설정
                SharedPreference.setString(SharedPreference.UNLOCK_MODE, SharedPreference.GESTURE);
                SharedPreference.setString(SharedPreference.GESTURE, gesture_input);
                //print
//                    Toast.makeText(getApplicationContext(), SharedPreference.getString(UNLOCK_MODE), LENGTH_LONG).show();
                String currentUnlockMode = SharedPreference.getString(SharedPreference.UNLOCK_MODE);
                if(currentUnlockMode==null){
                    Log.d("UNLOCK_MODE",  "null");
                }
                else{
                    Log.d("UNLOCK_MODE", currentUnlockMode);
                }
                String currentPin = SharedPreference.getString(SharedPreference.GESTURE);
                if(currentUnlockMode==null){
                    Log.d("UNLOCK_GESTURE",  "null");
                }
                else{
                    Log.d("UNLOCK_GESTURE", currentPin);
                }
                //확인 toast
                Toast.makeText(getApplicationContext(), "GESTURE setting done !", LENGTH_LONG).show();
                //뒤로
                finish();
            }
        });
    }

    /**
     * 1차 상보필터 적용 메서드 */
    private void complementaty (double new_ts) {

        /* 자이로랑 가속 해제 */
        gyroRunning = false;
        accRunning = false;

        /*센서 값 첫 출력시 dt(=timestamp - event.timestamp)에 오차가 생기므로 처음엔 break */
        if(timestamp == 0){
            timestamp = new_ts;
            return;
        }
        dt = (new_ts - timestamp) * NS2S; // ns->s 변환
        timestamp = new_ts;

        /* degree measure for accelerometer */
        mAccPitch = Math.atan2(mAccValues[1], mAccValues[2]) * 180.0 / Math.PI;     // X 축 기준
        mAccRoll = -Math.atan2(mAccValues[0], mAccValues[2]) * 180.0 / Math.PI;     // Y 축 기준
        mAccYaw = Math.atan2(mAccValues[0], mAccValues[1]) * 180.0 / Math.PI;       // Z 축 기준

        /**
         * 1st complementary filter.
         *  mGyroValuess : 각속도 성분.
         *  mAccPitch : 가속도계를 통해 얻어낸 회전각.
         */
        // pitch
        temp = (1/a) * (mAccPitch - pitch) + mGyroValues[1];
        pitch = pitch + (temp*dt);
        // roll
        temp = (1/a) * (mAccRoll - roll) + mGyroValues[0];
        roll = roll + (temp*dt);
        // yaw
        temp = (1/a) * (mAccYaw - yaw) + mGyroValues[2];
        yaw = yaw + (temp*dt);
        // increase or decrease numberPicker
        // pitch
        if(pitch_val_change){
            if(pitch >= 55){
                changeValueByOne(num_pitch, true);
            }
            else if(pitch < 0){
                changeValueByOne(num_pitch, false);
            }
        }
        // roll
        if(roll_val_change){
            if(roll >= 30){
                changeValueByOne(num_roll, true);
            }
            else if(roll < -30){
                changeValueByOne(num_roll, false);
            }
        }
        // yaw
        if(yaw_val_change){
            if(yaw >= 20){
                changeValueByOne(num_yaw, true);
            }
            else if(yaw < -20){
                changeValueByOne(num_yaw, false);
            }
        }
        // print textview
        tv_pitch.setText("pitch : " + Double.parseDouble(String.format("%.2f",pitch)));
        tv_roll.setText("roll : " + Double.parseDouble(String.format("%.2f",roll)));
        tv_yaw.setText("yaw : " + Double.parseDouble(String.format("%.2f",yaw)));
    }

    private void changeValueByOne (final NumberPicker higherPicker, final boolean increment) {

        Method method;
        try {
            // refelction call for
            // higherPicker.changeValueByOne(true);
            method = higherPicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(higherPicker, increment);

        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public class UserSensorListner implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()){
                /** GYROSCOPE */
                case Sensor.TYPE_GYROSCOPE:
                    /*센서 값을 mGyroValues에 저장*/
                    mGyroValues = event.values;
                    if(!gyroRunning)
                        gyroRunning = true;
                    break;
                /** ACCELEROMETER */
                case Sensor.TYPE_ACCELEROMETER:
                    /*센서 값을 mAccValues에 저장*/
                    mAccValues = event.values;
                    if(!accRunning)
                        accRunning = true;
                    break;
            }
            /**두 센서 새로운 값을 받으면 상보필터 적용*/
            if(gyroRunning && accRunning){
                complementaty(event.timestamp);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    }
}