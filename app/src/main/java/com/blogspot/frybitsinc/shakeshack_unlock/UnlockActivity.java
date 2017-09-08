package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by spong on 2017-09-07.
 */

public class UnlockActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView mTextAccX;
    private TextView mTextAccY;
    private TextView mTextAccZ;
//    private int acc_x;
//    private int acc_y;
//    private int acc_z;
    private TextView mTextGyroX;
    private TextView mTextGyroY;
    private TextView mTextGyroZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Accelerometer textviews
        mTextAccX = (TextView)findViewById(R.id.text_acc_x);
        mTextAccY = (TextView)findViewById(R.id.text_acc_y);
        mTextAccZ = (TextView)findViewById(R.id.text_acc_z);
        // Gyroscope textviews
        mTextGyroX = (TextView)findViewById(R.id.text_gyro_x);
        mTextGyroY = (TextView)findViewById(R.id.text_gyro_y);
        mTextGyroZ = (TextView)findViewById(R.id.text_gyro_z);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get accelerometer sensor data
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //raw data
//            acc_x = (int) event.values[0];
//            acc_y = (int) event.values[1];
//            acc_z = (int) event.values[2];
            // print
//            mTextAccX.setText(Integer.toString(acc_x));
//            mTextAccY.setText(Integer.toString(acc_y));
//            mTextAccZ.setText(Integer.toString(acc_z));

            // by applying a high-pass filter.
            final double alpha = 0.8;
            double [] gravity = {0, 0, 0};
            double [] linear_acceleration = {0, 0, 0};
            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];
            // print
            mTextAccX.setText(String.format("%.2f", linear_acceleration[0]));
            mTextAccY.setText(String.format("%.2f", linear_acceleration[1]));
            mTextAccZ.setText(String.format("%.2f", linear_acceleration[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
