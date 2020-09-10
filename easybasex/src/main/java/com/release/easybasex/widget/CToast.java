package com.release.easybasex.widget;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.release.easybasex.R;
import com.release.easybasex.base.BaseApplication;

/**
 * @author Mr.release
 * @create 2020/9/10
 * @Describe
 */

public class CToast {
    private static Toast mToast;

    private CToast() {
        throw new RuntimeException("CToast cannot be initialized!");
    }

    public static void show(String message, int duration) {
        if (mToast == null)
            mToast = new Toast(BaseApplication.getInstance());
        mToast.setDuration(duration);
        View view = View.inflate(BaseApplication.getInstance(), R.layout.cyc_c_toast, null);
        TextView textView = view.findViewById(R.id.tv_prompt);
        textView.setText(message);
        mToast.setView(view);
        mToast.setGravity(Gravity.BOTTOM, 0, 0);
        mToast.show();
    }

    public static void show(String message) {
        show(message, Toast.LENGTH_SHORT);
    }
}
