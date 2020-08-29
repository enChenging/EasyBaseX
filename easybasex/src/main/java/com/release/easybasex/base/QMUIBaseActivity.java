package com.release.easybasex.base;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.release.easybasex.R;
import com.release.easybasex.constance.BConstants;
import com.release.easybasex.event.NetworkChangeEvent;
import com.release.easybasex.receiver.NetworkChangeReceiver;
import com.release.easybasex.utils.AppManager;
import com.release.easybasex.utils.KeyBoardUtils;
import com.release.easybasex.utils.SPUtil;
import com.release.easybasex.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public abstract class QMUIBaseActivity extends QMUIActivity implements
        UiInterfaceAct {

    protected static String TAG;
    protected NetworkChangeReceiver mNetworkChangeReceiver;
    protected LinearLayoutCompat mBaseView;
    protected QMUITopBarLayout mTopBar;

    public boolean useEventBus() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initContentView(getLayoutId());

        mTopBar = findViewById(R.id.qmui_base_topbar);

        ButterKnife.bind(this);

        if (useEventBus())
            EventBus.getDefault().register(this);

        initView();

        initListener();

        AppManager.addActivity(this);

        startNet();
    }


    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
    }

    @Override
    public void startNet() {

    }

    public void doReConnected() {
        startNet();
    }


    private void initContentView(int layoutId) {
        View baseViewGroup = this.getLayoutInflater().inflate(R.layout.cyc_qmui_activity_base, null);
        mBaseView = baseViewGroup.findViewById(R.id.llc_base_view);
        LayoutInflater.from(this).inflate(layoutId, mBaseView, true);
        setContentView(baseViewGroup);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initReceiver();
        initThemeColor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver);
            mNetworkChangeReceiver = null;
        }
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkChangeReceiver, filter);
    }

    protected void initThemeColor() {
//        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.theme_color));

        //ActionBar颜色
//        if (this.getSupportActionBar() != null) {
//            this.getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.theme_color));
//        }

        //底部带返回键手机的导航栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.BLACK);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View currentFocus = getCurrentFocus();
            // 如果不是落在EditText区域，则需要关闭输入法
            if (KeyBoardUtils.isHideKeyboard(currentFocus, ev)) {
                KeyBoardUtils.hideKeyboard(this, currentFocus);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }


    @Override
    protected void onDestroy() {
        if (useEventBus())
            EventBus.getDefault().unregister(this);
        AppManager.removeActivity(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        SPUtil.setData(BConstants.HAS_NETWORK_KEY, event.isConnected());
        checkNetwork(event.isConnected());
    }

    private void checkNetwork(boolean isConnected) {
        if (isConnected) {
            doReConnected();
        } else {
            ToastUtils.show("请检查网络");
        }
    }
}
