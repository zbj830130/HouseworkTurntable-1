package com.bignerdranch.android.houseworkturntable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import business.HouseworkTurntable.ChartBus;
import business.HouseworkTurntable.HouseworkBus;
import business.HouseworkTurntable.HouseworkLab;
import models.HouseworkTurntable.HouseworkItem;

public class HouseworkItemViewPageActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<View> mViewList;
    private HouseworkItemViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housework_item_view_page);

        initToolbar();


        Intent intent = this.getIntent();
        HouseworkItem item = (HouseworkItem) intent.getSerializableExtra("hwItem");

        initViewPage(item.getId());

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewPage(int hwId) {
        int position = 0;
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewList = new ArrayList<>();

        List<HouseworkItem> hwItems = HouseworkLab.get(getApplicationContext()).getHouseworkItems();
        int i = 0;
        for (HouseworkItem hw : hwItems) {
            final int itemId = hw.getId();

            View view = LayoutInflater.from(this).inflate(R.layout.activity_housework_item, null);

            final TextView hwName = (TextView) view.findViewById(R.id.housework_name);
            hwName.setText(hw.getName());

            final CheckBox hwSelected = (CheckBox) view.findViewById(R.id.housework_selected);
            hwSelected.setChecked(hw.isSelected());

            Button hwSave = (Button) view.findViewById(R.id.housework_save);
            hwSave.setText("Save");
            hwSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = hwName.getText().toString();
                    if (Pattern.compile("^.{2,20}$").matcher(name).matches() == false) {
                        Toast toast = toast = Toast.makeText(getApplicationContext(), "Name must be longer than 2 characters and shorter than 20 characters ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
                        toast.show();
                        return;
                    }

                    new HouseworkBus(getApplicationContext()).updataItem(itemId,
                            name, hwSelected.isChecked());

                    new ChartBus(getApplicationContext()).updateHWName(itemId, name.toString());

                    Toast.makeText(getApplicationContext(), "Modify Successed", Toast.LENGTH_LONG).show();
                }
            });

            mViewList.add(view);

            if (hw.getId() == hwId) {
                position = i;
            }
            i++;
        }

        adapter = new HouseworkItemViewPageAdapter(mViewList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
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

    private class HouseworkItemViewPageAdapter extends PagerAdapter {
        private List<View> mViewList;

        public HouseworkItemViewPageAdapter(List<View> viewList) {
            this.mViewList = viewList;
        }


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position % mViewList.size()));
            return mViewList.get(position % mViewList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position % mViewList.size()));
        }
    }

}
