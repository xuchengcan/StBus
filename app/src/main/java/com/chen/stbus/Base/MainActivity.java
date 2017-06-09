package com.chen.stbus.Base;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;

import com.chen.stbus.BlankFragment;
import com.chen.stbus.Fragment.ItemListDialogFragment;
import com.chen.stbus.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements BlankFragment.OnFragmentInteractionListener,ItemListDialogFragment.Listener{

    private TextView mTextMessage;
    private ViewPager mViewPager;
    private BlankFragment mBlankFragment;
    private FragmentPagerAdapter mAdapter;
    private ItemListDialogFragment mItemListDialogFragment;
    List<Fragment> view;

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
                    mItemListDialogFragment = mItemListDialogFragment.newInstance(5);

                    getSupportFragmentManager().beginTransaction().show(mItemListDialogFragment).commit();
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

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        showToast(uri.getPath());
    }

    @Override
    public void onItemClicked(int position) {
        showToast("you click "+position);
    }
}
