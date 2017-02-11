package com.mj.materialdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mj.materialdemo.R;
import com.mj.materialdemo.adapter.FruitAdapter;
import com.mj.materialdemo.entity.Fruit;
import com.mj.materialdemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MainActivity
 * 实现双击退出
 */
public class MainActivity extends BasicActivity {
    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private List<Fruit> fruitList = new ArrayList<>();
    private Fruit[] fruits = {
            new Fruit("sooyoung", R.drawable.img_sooyoung),
            new Fruit("jessica", R.drawable.img_jessica),
            new Fruit("hyoyeon", R.drawable.img_hyoyeon),
            new Fruit("seohyun", R.drawable.img_seohyun),
            new Fruit("teayeon", R.drawable.img_taeyeon),
            new Fruit("yoona", R.drawable.img_yoona),
            new Fruit("tiffany", R.drawable.img_tiffany),
            new Fruit("yuri", R.drawable.img_yuri),
            new Fruit("sunny", R.drawable.img_sunny),
    };
    private FruitAdapter adapter;
    private long firstClick = 0;


    public static void actionStart(Activity act) {
        Intent intent = new Intent(act, MainActivity.class);
        act.startActivity(intent);
        act.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }

        initFruitList();
        adapter = new FruitAdapter(fruitList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        ToastUtils.showToast(MainActivity.this, "call!");
                        break;
                    case R.id.nav_friends:
                        ToastUtils.showToast(MainActivity.this, "friends!");
                        break;
                    case R.id.nav_location:
                        ToastUtils.showToast(MainActivity.this, "location!");
                        break;
                    case R.id.nav_mail:
                        ToastUtils.showToast(MainActivity.this, "mail!");
                        break;
                    case R.id.nav_task:
                        ToastUtils.showToast(MainActivity.this, "task!");
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruitList();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_backup:
                ToastUtils.showToast(this, "backup");
                break;
            case R.id.menu_delete:
                ToastUtils.showToast(this, "delete");
                break;
            case R.id.menu_settings:
                ToastUtils.showToast(this, "settings");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //双击退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick > 1000) {
                ToastUtils.showToast(this, "再次点击退出");
                firstClick = secondClick;
                return true;
            }else{
                //退出应用
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initFruitList() {
        fruitList.clear();
        Random random = new Random();
        for (int i = 0; i < 40; i++) {
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }
}
