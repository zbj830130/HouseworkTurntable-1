package com.bignerdranch.android.houseworkturntable;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import business.HouseworkTurntable.ChartBus;
import business.HouseworkTurntable.HouseworkBus;
import business.HouseworkTurntable.HouseworkLab;
import models.HouseworkTurntable.HouseworkItem;

public class MainActivity extends AppCompatActivity {

    private int countDown = 10;
    private TextView mCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        initActivity();

        startCountDownTime(countDown);
        databaseDefaultOperation();
    }

    private void initActivity() {
        mCountDown = (TextView) findViewById(R.id.countDown);
        mCountDown.setText(countDown + "");
    }

    //Create database and tables when app starts
    private void databaseDefaultOperation() {
        HouseworkBus bus = new HouseworkBus(getApplicationContext());
        if (bus.isInsetDefaultData() == true) {
            bus.insertDefaultData();
        }

        ChartBus mChartBus = new ChartBus(getApplicationContext());
        if (mChartBus.isInsetDefaultData() == true) {
            List<HouseworkItem> hwItems = HouseworkLab.get(getApplicationContext()).getHouseworkItems();
            for (HouseworkItem item :
                    hwItems) {
                mChartBus.addItem(item.getId(), item.getName());
            }
        }
    }

    //countdown and turn to a new activity
    private void startCountDownTime(long time) {
        CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCountDown.setText(millisUntilFinished / 1000 + "");

            }

            @Override
            public void onFinish() {
                final Intent mainIntent = new Intent(MainActivity.this, OptionsList.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };
        timer.start();
    }
}
