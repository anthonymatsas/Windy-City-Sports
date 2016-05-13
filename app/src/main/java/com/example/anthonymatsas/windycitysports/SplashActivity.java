package com.example.anthonymatsas.windycitysports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    //Class level variable
    private Timer timer = new Timer();

    protected void onCreate(Bundle savedInstanceState) {
        //Show splash page for set milliseconds
        super.onCreate(savedInstanceState);

        timer.schedule(new TimerTask() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainTeamsActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
