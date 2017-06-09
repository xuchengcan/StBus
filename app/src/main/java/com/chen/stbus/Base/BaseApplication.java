package com.chen.stbus.Base;

import android.app.Application;
import android.content.Context;

/**
 * Created by chen on 2017/6/9.
 */

public class BaseApplication extends Application {

    private static Context mContext;

    public BaseApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
