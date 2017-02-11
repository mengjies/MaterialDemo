package com.mj.materialdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by MengJie on 2017/1/11.
 * 获取全局Context
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
