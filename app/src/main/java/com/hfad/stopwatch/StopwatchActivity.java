package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {

    private boolean isRunning = false;
    private int secondsPassed = 0;
    private boolean wasRunning = false;

    private void runTimer() {
        final TextView timerView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = secondsPassed/3600;
                int minutes = (secondsPassed%3600)/60;
                int seconds = secondsPassed%60;

                String timerString = String.format(Locale.getDefault(), "%d:%02d:%02d",hours,minutes,seconds);
                timerView.setText(timerString);

                if(isRunning) {
                    secondsPassed++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if ( savedInstanceState != null ) {
            secondsPassed = savedInstanceState.getInt("secondsPassed");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    public void onClickStart(View view) {
        isRunning = true;
    }

    public void onClickStop(View view) {
        isRunning = false;
    }

    public void onClickReset(View view) {
        isRunning = false;
        secondsPassed = 0;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("secondsPassed",secondsPassed);
        savedInstanceState.putBoolean("isRunning",isRunning);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    public void onStop() {
        super.onStop();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(wasRunning) {
            isRunning = true;
        }
    }

}
