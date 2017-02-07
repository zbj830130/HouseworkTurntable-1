package com.bignerdranch.android.houseworkturntable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import business.HouseworkTurntable.ChartBus;
import business.HouseworkTurntable.HouseworkBus;
import business.HouseworkTurntable.HouseworkLab;
import models.HouseworkTurntable.HouseworkItem;

public class OptionsList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mHouseworkRecyclerView;
    private HouseworkAdapter mHouseworkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_list);

        initToolBar();
        initRecyclerView();

    }

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

    private void initRecyclerView() {
        mHouseworkRecyclerView = (RecyclerView) findViewById(R.id.housework_recycler_view);

        DividerItemDecoration did = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        did.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.item_line_divider));

        mHouseworkRecyclerView.addItemDecoration(did);
        mHouseworkRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final HouseworkLab houseworkLab = HouseworkLab.get(this);

        updateHouseworkList(houseworkLab);

        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                HouseworkItem item = houseworkLab.getHouseworkItems().remove(position);
                mHouseworkAdapter.notifyItemRemoved(position);
                mHouseworkAdapter.notifyItemRangeChanged(0, houseworkLab.getHouseworkItems().size());
                new HouseworkBus(getApplicationContext()).deleteItem(item.getId());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
                    , float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(mHouseworkRecyclerView);
    }

    private void updateHouseworkList(HouseworkLab houseworkLab) {

        List<HouseworkItem> items = houseworkLab.getHouseworkItems();

//        if (mHouseworkAdapter == null) {
        mHouseworkAdapter = new HouseworkAdapter(items);
        mHouseworkRecyclerView.setAdapter(mHouseworkAdapter);


//        } else {
//            mHouseworkAdapter.notify();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HouseworkLab houseworkLab = HouseworkLab.get(getApplicationContext());
        updateHouseworkList(houseworkLab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_options_list_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_menu_add) {
            Intent intent = new Intent(OptionsList.this, HouseworkItemActivity.class);
            intent.putExtra("hwId", 0);
            startActivityForResult(intent, 100);
        } else if (id == R.id.main_menu_run) {
            List<HouseworkItem> items = HouseworkLab.get(this).getHouseworkItems();

            int selectedCount = 0;
            for (HouseworkItem hwItem : items) {
                if (hwItem.isSelected() == true) {
                    selectedCount++;
                }
            }

            Toast toast = null;

            if (selectedCount < 2) {
                toast = Toast.makeText(this, "The number of choices must be more than 1 !", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
                toast.show();
                return false;
            }

            if (selectedCount > 6) {
                toast = Toast.makeText(this, "The number of choices must be less than 12 !", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
                toast.show();
                return false;
            }

            Intent intent = new Intent(OptionsList.this, HouseworkPanActivity.class);
            startActivityForResult(intent, 101);

            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chart) {
            final Intent chartIntent = new Intent(OptionsList.this, ChartActivity.class);
            OptionsList.this.startActivity(chartIntent);
            OptionsList.this.finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 || requestCode == 102) {
            Context context = getApplicationContext();
            HouseworkLab lab = HouseworkLab.get(context);
            lab.Refresh(context);

            updateHouseworkList(HouseworkLab.get(getApplicationContext()));
        }
    }


    private class HouseworkHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView mNameTextView;
        private CheckBox mCheckBox;
        private HouseworkItem mItem;


        public HouseworkHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);

            mNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_housework_name_text_view);
            mCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_housework_check_box);

        }

        public void bindHouseworkItem(final HouseworkItem item) {
            mItem = item;
            mNameTextView.setText(item.getName());
            mCheckBox.setChecked(item.isSelected());

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Context context = getApplicationContext();
                    HouseworkLab.get(context)
                            .getHouseworkItem(item.getId()).setSelected(isChecked);

                    new HouseworkBus(context)
                            .updateSelected(item.getId(), isChecked);

                }
            });
        }


        @Override
        public boolean onLongClick(View v) {
            Intent intent = new Intent(OptionsList.this, HouseworkItemViewPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("hwItem", mItem);
            intent.putExtras(bundle);
            startActivityForResult(intent, 102);
            return true;
        }
    }

    private class HouseworkAdapter extends RecyclerView.Adapter<HouseworkHolder> {

        private List<HouseworkItem> mItems;

        public HouseworkAdapter(List<HouseworkItem> items) {
            mItems = items;
        }

        @Override
        public HouseworkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_housework, parent, false);
            return new HouseworkHolder(view);
        }

        @Override
        public void onBindViewHolder(final HouseworkHolder holder, final int position) {
            HouseworkItem item = mItems.get(position);
            holder.bindHouseworkItem(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

}
