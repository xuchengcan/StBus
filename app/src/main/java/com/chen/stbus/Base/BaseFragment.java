package com.chen.stbus.Base;

import android.support.v4.app.Fragment;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.chen.stbus.Utils.TextUtil;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by chen on 2017/6/12.
 */

public class BaseFragment extends Fragment {

    public void showToast(String content) {

        if (TextUtil.isValidate(content)) {
            Toast.makeText(BaseApplication.getContext(), content, Toast.LENGTH_SHORT).show();
            //  Snackbar.make(getWindow().getDecorView(), content, Snackbar.LENGTH_LONG).show();
        }

    }

    public Map<String, String> getUrlhead() {
        Map<String, String> urlhead = new HashMap<>();
        urlhead.put("Accept", "application/json");
        urlhead.put("Accept-Encoding","gzip,deflate");
        urlhead.put("Accept-Language", "zh-CN,en-US;q=0.8");
        urlhead.put("User-Agent", WebSettings.getDefaultUserAgent(getContext()) + " Html5Plus/1.0");
        urlhead.put("X-Requested-With", "XMLHttpRequest");
        urlhead.put("Content-Type", "application/x-www-form-urlencoded,charset=UTF-8");
        urlhead.put("Origin", "file://");
        return urlhead;
    }

    public void connectServer(){
        OkHttpUtils.post()
                .headers(getUrlhead())
                .addParams("userID", "guest")
                .addParams("phoneType", "1")
                .addParams("appId", "Z3MXkuYTzB7Cyu3petSbC1")//Z3MXkuYTzB7Cyu3petSbC1
                .addParams("appKey", "fRPiisiGhD7sbL3PulBri9")//fRPiisiGhD7sbL3PulBri9
                .addParams("clientId", "92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .addParams("token", "92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .addParams("alias", "undefined")//undefined
                .addParams("busAppId", "com.gmcc.stgj")//com.gmcc.stgj
                .addParams("busVersion", "1.9.2")//1.9.2
                .addParams("busAppName", "汕头公交")//汕头公交
                .addParams("phoneImei", "861843037514859,861843037514842")//861843037514859,861843037514842
                .addParams("phoneImsi", "")//*
                .addParams("phoneModel", "vivo Y51A")//vivo Y51A
                .addParams("phoneVendor", "vivo")//vivo
                .addParams("phoneUuid", "861843037514859,861843037514842")//861843037514859,861843037514842
                .addParams("phoneSysName", "Android")//Android
                .addParams("phoneVersion", "5.0.2")//5.0.2
                .addParams("phoneLanguage", "zh_CN")//zh_CN
                .addParams("ticket", "92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .url(UrlHelper.BaseUrl + UrlHelper.CreateBizGetui + "?ticket=" + UrlHelper.Ticket)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                KLog.e();
                showToast("连接服务器失败，请检查网络或重新打开应用");
            }

            @Override
            public void onResponse(String response, int id) {
                KLog.i(response);
                if (response.contains("0")) {
                } else {
                    showToast(response);
                }
            }
        });
    }
}
