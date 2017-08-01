package com.chen.stbus.Base;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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

        KLog.init(true,"CHEN");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("CHEN"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        SDKInitializer.initialize(getApplicationContext());
    }

    public static Context getContext() {
        return mContext;
    }
}
