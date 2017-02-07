package com.bignerdranch.android.houseworkturntable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import models.HouseworkTurntable.HouseworkItem;

public class HouseworkPanActivity extends AppCompatActivity {

    private HouseworkPanBusiness mPanBusiness;
    private ImageView mStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_housework_pan);

        mPanBusiness = (HouseworkPanBusiness) findViewById(R.id.id_houseworkpan);
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);


        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! mPanBusiness.isStart()){
                    mPanBusiness.panStart();
                    mStartBtn.setImageResource(R.drawable.stop);
                }else{
                    if(! mPanBusiness.isShouldEnd()){
                        mPanBusiness.panEnd();
                        mStartBtn.setImageResource(R.drawable.start);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
