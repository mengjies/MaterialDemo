package com.mj.materialdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mj.materialdemo.R;

public class FruitActivity extends BasicActivity {

    private String fruitName;
    private int fruitId;

    public static void actionStart(Context context, String fruitName, int fruitId) {
        Intent intent = new Intent(context, FruitActivity.class);
        intent.putExtra("name", fruitName);
        intent.putExtra("id", fruitId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent = getIntent();
        fruitName = intent.getStringExtra("name");
        fruitId = intent.getIntExtra("id",0);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView ivFruit = (ImageView) findViewById(R.id.iv_fruit);
        TextView tvFruitContent = (TextView) findViewById(R.id.tv_fruit_content);
        FloatingActionButton floatButton = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        collapsingToolbar.setTitle(fruitName);
        Glide.with(this).load(fruitId).into(ivFruit);
        String fruitContent = generateFruitContent(fruitName);
        tvFruitContent.setText(fruitContent);
    }

    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for(int i = 0; i < 500; i++) {
            fruitContent.append(fruitName + "");
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
