package com.chen.stbus.Base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chen.stbus.Utils.TextUtil;

/**
 * Created by chen on 2017/6/9.
 */

public class BaseActivity extends AppCompatActivity {



    public void showToast(String content) {

        if (TextUtil.isValidate(content)) {
            Toast.makeText(BaseApplication.getContext(), content, Toast.LENGTH_SHORT).show();
            //  Snackbar.make(getWindow().getDecorView(), content, Snackbar.LENGTH_LONG).show();
        }

    }
}
