package uk.ac.shef.oak.com6510.sensor;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

public class Barometer {

    private static final String TAG = Barometer.class.getSimpleName();
    private long mSamplingRateNano;
    private long mSamplingRateInMSecs;
    private SensorEventListener mPressureListener = null;
    private SensorManager mSensorManager;
    private Sensor mBarometerSensor;
    private long timePhoneWasLastRebooted = 0;
    private long BAROMETER_READING_FREQUENCY= 30000;
    private long lastReportTime = 0;
    private float pressureValue;
    /**
     * this is used to stop the barometer if we have not seen any movement in the last 20 seconds
     */
    private static final long STOPPING_THRESHOLD = (long)20000;


    public Barometer(Context context) {
        SystemClock.elapsedRealtime();
        mSamplingRateNano = (long) (BAROMETER_READING_FREQUENCY) * 1000000;
        mSamplingRateInMSecs = (long) BAROMETER_READING_FREQUENCY;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // get a Barometer Sensor, according to the object of SensorManager
        mBarometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        initBarometerListener();
    }

    public float getPressureValue(){
        return pressureValue;
    }

    private void setPressureValue(float p){
        pressureValue = p;
    }

    // initialize the barometer sensor listener
    private void initBarometerListener() {
        // check if the  Barometer Sensor is available for the device
        if (mBarometerSensor == null) {
            Log.d(TAG, "Standard Barometer unavailable");
        }
        else {
            Log.d(TAG, "Using Barometer");
            mPressureListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    long diff = event.timestamp - lastReportTime;
                    if (diff >= mSamplingRateNano) {
                        long actualTimeInMseconds = timePhoneWasLastRebooted + (long) (event.timestamp / 1000000.0);
                        setPressureValue(event.values[0]);
                        int accuracy = event.accuracy;
                        lastReportTime = event.timestamp;
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }


            };
        }
    }

    // starts the pressure monitoring
    public void startSensingPressure() {
        if (mBarometerSensor != null) {
            Log.d("Standard Barometer", "starting listener");

            // delay is in microseconds (1 millisecond = 1000 microseconds)
            // it does not seem to work though
            mSensorManager.registerListener(mPressureListener, mBarometerSensor, (int) (mSamplingRateInMSecs * 1000));
            Log.i(TAG, "barometer unavailable or already active");
        }
    }

    // stops the barometer
    public void stopBarometer() {
        if (mBarometerSensor != null) {
            Log.d("Standard Barometer", "Stopping listener");
            try {
                mSensorManager.unregisterListener(mPressureListener);
            } catch (Exception e) {

            }
        }
    }


}
