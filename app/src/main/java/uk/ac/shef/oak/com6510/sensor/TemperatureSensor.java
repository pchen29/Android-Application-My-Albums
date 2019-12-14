package uk.ac.shef.oak.com6510.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class TemperatureSensor {

    private static final String TAG = TemperatureSensor.class.getSimpleName();
    private SensorEventListener mTemperatureListener = null;
    private SensorManager mSensorManager;
    private Sensor mTemperatureSensor;

    public TemperatureSensor(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // get a Temperature Sensor, according to the object of SensorManager
        mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        initTempSensorListener();
    }

    // initialize the temperature sensor listener
    private void initTempSensorListener(){
        if(mTemperatureSensor == null){
            Log.d(TAG, "Standard Temperature Sensor unavailable");
        }else{

            mTemperatureListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float temperatureValue = event.values[0];
                    int accuracy = event.accuracy;
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }


            };

        }
    }

    // starts the temperature monitoring
    public void startSensingPressure() {
        if (mTemperatureSensor != null) {
            Log.d("Standard Barometer", "starting listener");

            // delay is in microseconds (1 millisecond = 1000 microseconds)
            // it does not seem to work though
            mSensorManager.registerListener(mTemperatureListener, mTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.i(TAG, "barometer unavailable or already active");
        }
    }

    // stops the temperature monitoring
    public void stopBarometer() {
        if (mTemperatureSensor != null) {
            Log.d(TAG, "Stopping listener");
            try {
                mSensorManager.unregisterListener(mTemperatureListener);

            } catch (Exception e) {

            }
        }
    }
}
