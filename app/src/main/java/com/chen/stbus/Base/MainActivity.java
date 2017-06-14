package com.chen.stbus.Base;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;

import com.chen.stbus.BlankFragment;
import com.chen.stbus.Fragment.ItemListDialogFragment;
import com.chen.stbus.R;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends BaseActivity implements BlankFragment.OnFragmentInteractionListener,ItemListDialogFragment.Listener{

    private TextView mTextMessage;
    private ViewPager mViewPager;
    private BlankFragment mBlankFragment;
    private FragmentPagerAdapter mAdapter;
    private ItemListDialogFragment mItemListDialogFragment;
    private List<Fragment> view;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    mItemListDialogFragment = mItemListDialogFragment.newInstance(15);

                    // TODO: 2017/6/9 chennuo学习SupportFragmentManager
                    getSupportFragmentManager().beginTransaction().add(mItemListDialogFragment,"aa").commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mBlankFragment = mBlankFragment.newInstance("aaa","bbb");
        mItemListDialogFragment = mItemListDialogFragment.newInstance(5);

        view = new ArrayList<>();
        view.add(mBlankFragment);
//        view.add(mItemListDialogFragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return view.get(position);
            }

            @Override
            public int getCount() {
                return view.size();
            }
        };
        mViewPager.setAdapter(mAdapter);

        content2Server();
    }




    @Override
    public void onFragmentInteraction(Uri uri) {
        showToast(uri.getPath());
    }

    @Override
    public void onItemClicked(int position) {


        showToast("you click "+position);
    }

    private void content2Server() {

        OkHttpUtils.post()
                .headers(getUrlhead())
                .addParams("userID", "guest")
                .addParams("phoneType", "1")
                .addParams("appId","Z3MXkuYTzB7Cyu3petSbC1")//Z3MXkuYTzB7Cyu3petSbC1
                .addParams("appKey","fRPiisiGhD7sbL3PulBri9")//fRPiisiGhD7sbL3PulBri9
                .addParams("clientId","92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .addParams("token","92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .addParams("alias","undefined")//undefined
                .addParams("busAppId","com.gmcc.stgj")//com.gmcc.stgj
                .addParams("busVersion","1.9.2")//1.9.2
                .addParams("busAppName","汕头公交")//汕头公交
                .addParams("phoneImei","861843037514859,861843037514842")//861843037514859,861843037514842
                .addParams("phoneImsi","")//*
                .addParams("phoneModel","vivo Y51A")//vivo Y51A
                .addParams("phoneVendor","vivo")//vivo
                .addParams("phoneUuid","861843037514859,861843037514842")//861843037514859,861843037514842
                .addParams("phoneSysName","Android")//Android
                .addParams("phoneVersion","5.0.2")//5.0.2
                .addParams("phoneLanguage","zh_CN")//zh_CN
                .addParams("ticket","92a4595b68ce889ade08d5dc00ff0c68")//92a4595b68ce889ade08d5dc00ff0c68
                .url(UrlHelper.BaseUrl + UrlHelper.CreateBizGetui + "?ticket=" + UrlHelper.Ticket)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                KLog.e();
            }

            @Override
            public void onResponse(String response, int id) {

                if (response.contains("0")){
                    showToast("连接服务器成功");
                    Snackbar.make(getCurrentFocus(),"连接服务器成功", BaseTransientBottomBar.LENGTH_LONG);
                }
//                showToast(response);
            }
        });
    }
}
