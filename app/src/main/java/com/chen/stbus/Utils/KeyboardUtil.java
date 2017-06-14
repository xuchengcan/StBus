package com.chen.stbus.Utils;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chen.stbus.Base.BaseApplication;

/**
 * Created by chen on 2017/6/14.
 */

public class KeyboardUtil {
    /**
     * 打开软键盘
     *
     * @param mContext  上下文
     * @param mEditText 输入框
     */
    public static void openKeyboard(Context mContext, EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     */
    public static void closeKeyboard(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) BaseApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public static void closeKeyboard(IBinder iBinder) {
        InputMethodManager imm = (InputMethodManager) BaseApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(iBinder, 0);
    }
}
