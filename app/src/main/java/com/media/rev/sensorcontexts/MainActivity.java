    package com.media.rev.sensorcontexts;

    import android.app.Activity;
    import android.content.Context;
    import android.hardware.Sensor;
    import android.hardware.SensorEvent;
    import android.hardware.SensorEventListener;
    import android.hardware.SensorManager;
    import android.os.Bundle;
    import android.widget.SeekBar;


    public class MainActivity extends Activity implements SensorEventListener  {

        private SensorManager sense_mgr;
        private Sensor accelo_sensor;

        private int sensorDelay = 0;

        private float[]acceloData = new float[3];
        private AcceloView acceloview;
        private SeekBar seek;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



            sense_mgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            accelo_sensor = sense_mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


            acceloview = (AcceloView) findViewById(R.id.acceleroView);

            seek = (SeekBar)findViewById(R.id.seekBarSampleRate);

            seek.setProgress(sensorDelay * 30);

            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                sensorDelay = progresValue / 30;
                resetSensorDelay();

             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {}
             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {
                sensorDelay = seekBar.getProgress() / 30;
                resetSensorDelay(); }
              });

        }
         @Override
         public final void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Do something here if sensor accuracy changes.
            }

         @Override
         public final void onSensorChanged(SensorEvent event) {

             acceloData = event.values;

             acceloview.addData(event.values);
             acceloview.invalidate();

         }
          @Override
          protected void onResume() {
                super.onResume();

                sense_mgr.unregisterListener(this);
                sense_mgr.registerListener(this, accelo_sensor, sensorDelay);
            }

          @Override
          protected void onPause() {
                super.onPause();
                sense_mgr.unregisterListener(this);
            }

        private void resetSensorDelay(){
            sense_mgr.unregisterListener(this);
            sense_mgr.registerListener(this, accelo_sensor, sensorDelay);

        }
    }
