package com.blogspot.frybitsinc.shakeshack_unlock;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by spong on 2017-09-07.
 */

public class UnlockActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    // ACC
    private Sensor mAccelerometer;
    private TextView mTextAccX;
    private TextView mTextAccY;
    private TextView mTextAccZ;
    // GYRO
    private Sensor mGyroscope;
    // roll, pitch, yaw
    private double pitch;
    private double roll;
    private double yaw;
    //timestamp, dt
    private double timestamp;
    private double dt;
    // radian -> degree
    private double RADIAN_TO_DEGREE = 180 / Math.PI;
    private static final float NANOSEC_TO_SEC = 1.0f / 1000000000.0f;
    private TextView mTextGyroX;
    private TextView mTextGyroY;
    private TextView mTextGyroZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
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
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_UI);
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

        // get gyroscope sensor data
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            /* 각 축의 각속도 성분을 받는다. */
            double gyroX = event.values[0];
            double gyroY = event.values[1];
            double gyroZ = event.values[2];
             /* 각속도를 적분하여 회전각을 추출하기 위해 적분 간격(dt)을 구한다.
             * dt : 센서가 현재 상태를 감지하는 시간 간격
             * NS2S : nano second -> second */
            dt = (event.timestamp - timestamp) * NANOSEC_TO_SEC;
            timestamp = event.timestamp;
            /* 맨 센서 인식을 활성화 하여 처음 timestamp가 0일때는 dt값이 올바르지 않으므로 넘어간다. */
            if (dt - timestamp*NANOSEC_TO_SEC != 0) {
                /* 각속도 성분을 적분 -> 회전각(pitch, roll)으로 변환.
                 * 여기까지의 pitch, roll의 단위는 '라디안'이다.
                 * SO 아래 로그 출력부분에서 멤버변수 'RAD2DGR'를 곱해주어 degree로 변환해줌.  */
                pitch = pitch + gyroY*dt;
                roll = roll + gyroX*dt;
                yaw = yaw + gyroZ*dt;

                Log.e("LOG", "GYROSCOPE           [X]:" + String.format("%.4f", event.values[0])
                        + "           [Y]:" + String.format("%.4f", event.values[1])
                        + "           [Z]:" + String.format("%.4f", event.values[2])
                        + "           [Pitch]: " + String.format("%.1f", pitch*RADIAN_TO_DEGREE)
                        + "           [Roll]: " + String.format("%.1f", roll*RADIAN_TO_DEGREE)
                        + "           [Yaw]: " + String.format("%.1f", yaw*RADIAN_TO_DEGREE)
                        + "           [dt]: " + String.format("%.4f", dt));
                // print
                mTextGyroX.setText(String.format("%.2f", pitch*RADIAN_TO_DEGREE));
                mTextGyroY.setText(String.format("%.2f", roll*RADIAN_TO_DEGREE));
                mTextGyroZ.setText(String.format("%.2f", yaw*RADIAN_TO_DEGREE));
//                mTextGyroX.setText(String.format("%.2f", gyroX));
//                mTextGyroY.setText(String.format("%.2f", gyroY));
//                mTextGyroZ.setText(String.format("%.2f", gyroZ));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
