package com.release.simplex.ui.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.release.easybasex.base.BaseActivity;
import com.release.easybasex.utils.ToastUtils;
import com.release.simplex.R;
import com.release.simplex.widget.QuickWindowFloatView;

import butterknife.OnClick;

/**
 * @author Mr.release
 * @create 2020/5/6
 * @Describe
 */

public class ScreenshotAndWindowFloatActivity extends BaseActivity {

    /**
     * 截图权限
     */
    public static final int REQUEST_MEDIA_PROJECTION = 18;
    /**
     * 悬浮窗权限
     */
    public static final int REQUEST_FLOAT_UI = 19;

    private MediaProjectionManager mMediaProjectionManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_screenshot_windowfloat;
    }

    @Override
    public void initView() {
        mTopBar.setTitle("截图与悬浮窗");
    }

    @OnClick(R.id.acb_screenshot)
    public void onViewClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_FLOAT_UI);
            } else {
                requestCapturePermission();
            }
        } else {
            requestCapturePermission();
        }
    }

    private void requestCapturePermission() {
        //获取截屏的管理器
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
    }

    private void showFloat(MediaProjection mediaProjection) {
        QuickWindowFloatView quickWindowFloatView = new QuickWindowFloatView(this, mediaProjection);
        quickWindowFloatView.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION:
                if (resultCode == RESULT_OK && data != null) {
                    MediaProjection mMediaProjection = mMediaProjectionManager.getMediaProjection(Activity.RESULT_OK, data);
                    showFloat(mMediaProjection);
                }
                break;
            case REQUEST_FLOAT_UI:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                        requestCapturePermission();
                    } else {
                        ToastUtils.show("ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝");
                    }
                }
        }
    }
}
