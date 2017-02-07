package com.bignerdranch.android.houseworkturntable;

import android.content.Intent;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import business.HouseworkTurntable.ChartBus;
import business.HouseworkTurntable.HouseworkBus;

public class HouseworkItemActivity extends AppCompatActivity {

    private EditText mTitleField;
    private Button mSaveButton;
    private CheckBox mSelected;
    private HouseworkBus bus;
    private ChartBus mChartBus;
    private int hwId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_housework_item);
        bus = new HouseworkBus(getApplicationContext());
        mChartBus = new ChartBus(getApplicationContext());

        hwId = getIntent().getIntExtra("hwId", 0);

        InitActivity();
    }

    private void InitActivity() {
        mTitleField = (EditText) findViewById(R.id.housework_name);

        mSaveButton = (Button) findViewById(R.id.housework_save);
        mSaveButton.setText("Save");
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mTitleField.getText().toString();
                if (Pattern.compile("^.{2,20}$").matcher(name).matches() == false) {
                    Toast toast = toast = Toast.makeText(getApplicationContext(), "Name must be longer than 2 characters and shorter than 20 characters ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
                    toast.show();
                    return;
                }

                int id = bus.addItem(name, mSelected.isChecked());
                mChartBus.addItem(id, mTitleField.getText().toString());
            }
        });

        mSelected = (CheckBox) findViewById(R.id.housework_selected);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra("hwId", 0);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
