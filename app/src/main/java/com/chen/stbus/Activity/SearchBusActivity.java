package com.chen.stbus.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chen.stbus.Base.BaseActivity;
import com.chen.stbus.Base.UrlHelper;
import com.chen.stbus.R;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


public class SearchBusActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                initServer();
            }
        });

        initFragment();
    }

    private void initServer() {


//        OkHttpUtils.post()
//                .url("https://service.dcloud.net.cn/collect/plusapp/startup")
//                .addParams("appid", "com.gmcc.stgj")
//                .addParams("imei", "RFRHHEpSTXXvqT9c6F4g5+86bTLe89dBVTiWCIq5xQWI2AILlsqfx9MzBC829F7KnGDEIl3IPhym/ZqDHDM2boe9fi12fxnoHsoaeXfrWNEvCep0hhNf+tulikY7QTUQS3BjZXgtifCoB++FlHx1AA==")
//                .addParams("ie", "1")
//                .addParams("net", "3")
//                .addParams("md", "vivo Y51A")
//                .addParams("os", "21")
//                .addParams("vb", "1.9.9.31836")
//                .addParams("sf", "1")
//                .addParams("p", "a")
//                .addParams("d1", "1497421060706")
//                .addParams("sfd", "null")
//                .addParams("vd", "vivo")
//                .addParams("__am", "t")
//                .addParams("mc", "com.gmcc.stgj")
//                .addParams("st", "1712")
//                .addParams("pn", "com.gmcc.stgj")
//                .addParams("v", "1.9.2")
//                .addParams("pv", "1.9.2")
//                .addParams("name", "汕头公交")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        KLog.e();
//                        showToast("dcloud : error");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        KLog.i("dcloud : " + response);
//                        showToast("dcloud : " + response);
//                    }
//                });


        OkHttpUtils.post()
                .headers(getUrlhead())
                .url(UrlHelper.BaseUrl + UrlHelper.LogRecord + "?ticket=" + UrlHelper.Ticket)
                .addParams("ticket", UrlHelper.Ticket)
                .addParams("log", "eyJwdSI6Ijg2MTg0MzAzNzUxNDg1OSw4NjE4NDMwMzc1MTQ4NDIiLCJwdiI6InZpdm8iLCJwbSI6InZpdm8gWTUxQSIsImF2IjoiMS45LjIiLCJwc24iOiJBbmRyb2lkIiwicHZuIjoiNS4wLjIiLCJyIjoiMC40MDQ0NTQ0NDM2MDM3NTQwNCJ9")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e();
                        showToast("LogRecord : error");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        KLog.i("LogRecord : " + response);
                        showToast("LogRecord : " + response);
                    }
                });


        OkHttpUtils.post()
                .headers(getUrlhead())
                .url(UrlHelper.BaseUrl + UrlHelper.Version + "?ticket=" + UrlHelper.Ticket)
                .addParams("ticket", UrlHelper.Ticket)
                .addParams("version", "1.9.2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e();
                        showToast("Version : error");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        KLog.i("Version : " + response);
                        showToast("Version : " + response);
                    }
                });

        OkHttpUtils.post()
                .headers(getUrlhead())
                .url(UrlHelper.BaseUrl + UrlHelper.GetAd + "?ticket=" + UrlHelper.Ticket)
                .addParams("ticket", UrlHelper.Ticket)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e();
                        showToast("GetAd : error");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        KLog.i("GetAd : " + response);
                        showToast("GetAd : " + response);
                    }
                });

        connectServer();
    }

    private void initFragment() {


    }

}
