package com.bignerdranch.android.houseworkturntable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import business.HouseworkTurntable.ChartBus;
import business.HouseworkTurntable.HouseworkLab;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;
import models.HouseworkTurntable.HouseworkItem;
import models.HouseworkTurntable.TurntableCountItem;

public class ChartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PieChartView pieChart;
    private PieChartData pieChardata;
    List<SliceValue> values = new ArrayList<SliceValue>();
    private ChartBus mChartBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        initToolBar();
        mChartBus = new ChartBus(getApplicationContext());


        pieChart = (PieChartView) findViewById(R.id.pie_chart);
        pieChart.setOnValueTouchListener(selectListener);

        setPieChartData();
        initPieChart();
    }


    private void setPieChartData() {

        List<TurntableCountItem> items = mChartBus.queryTurntableCountItems();
        for (TurntableCountItem item : items) {
            SliceValue sliceValue = new SliceValue(item.getCountNum(), ChartUtils.pickColor());
            sliceValue.setLabel(item.getHouseworkName() + ":" + item.getCountNum());
            if (sliceValue.getValue() > 0) {
                values.add(sliceValue);
            }
        }
    }

    private void initPieChart() {
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);
        pieChardata.setHasLabelsOnlyForSelected(false);
        pieChardata.setHasLabelsOutside(false);
        pieChardata.setHasCenterCircle(false);
        pieChardata.setValues(values);
        pieChardata.setCenterCircleColor(Color.WHITE);
        pieChardata.setCenterCircleScale(0.5f);
        pieChart.setPieChartData(pieChardata);
        pieChart.setValueSelectionEnabled(true);
        pieChart.setAlpha(0.9f);
        pieChart.setCircleFillRatio(1f);

    }


    /**
     * 监听事件
     */
    private PieChartOnValueSelectListener selectListener = new PieChartOnValueSelectListener() {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onValueSelected(int arg0, SliceValue value) {
            // TODO Auto-generated method stub
            Toast.makeText(ChartActivity.this, "Selected: " + value.getValue(), Toast.LENGTH_SHORT).show();
        }
    };

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_turntable) {
            final Intent chartIntent = new Intent(ChartActivity.this, OptionsList.class);
            ChartActivity.this.startActivity(chartIntent);
            ChartActivity.this.finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
