package com.release.simplex.downLoad;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.release.simplex.R;

/**
 * @author Mr.release
 * @create 2020/4/24
 * @Describe
 */

public class DownLoadActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 001;
    TextView tv_file_name1, tv_progress1, tv_file_name2, tv_progress2;
    Button btn_download1, btn_download2, btn_download_all;
    ProgressBar pb_progress1, pb_progress2;

    DownloadManager mDownloadManager;
    String wechatUrl = "https://dldir1.qq.com/weixin/android/weixin657android1040.apk";
    String qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        initViews();
        initDownloads();
    }

    private void initDownloads() {
        mDownloadManager = DownloadManager.getInstance();
        mDownloadManager.add(wechatUrl, new DownloadListner() {
            @Override
            public void onFinished() {
                Toast.makeText(DownLoadActivity.this, "下载完成!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(float progress) {
                pb_progress1.setProgress((int) (progress * 100));
                tv_progress1.setText(String.format("%.2f", progress * 100) + "%");
            }

            @Override
            public void onPause() {
                Toast.makeText(DownLoadActivity.this, "暂停了!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                tv_progress1.setText("0%");
                pb_progress1.setProgress(0);
                btn_download1.setText("下载");
                Toast.makeText(DownLoadActivity.this, "下载已取消!", Toast.LENGTH_SHORT).show();
            }
        });

        mDownloadManager.add(qqUrl, new DownloadListner() {
            @Override
            public void onFinished() {
                Toast.makeText(DownLoadActivity.this, "下载完成!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(float progress) {
                pb_progress2.setProgress((int) (progress * 100));
                tv_progress2.setText(String.format("%.2f", progress * 100) + "%");
            }

            @Override
            public void onPause() {
                Toast.makeText(DownLoadActivity.this, "暂停了!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                tv_progress2.setText("0%");
                pb_progress2.setProgress(0);
                btn_download2.setText("下载");
                Toast.makeText(DownLoadActivity.this, "下载已取消!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化View控件
     */
    private void initViews() {
        tv_file_name1 = (TextView) findViewById(R.id.tv_file_name1);
        tv_progress1 = (TextView) findViewById(R.id.tv_progress1);
        pb_progress1 = (ProgressBar) findViewById(R.id.pb_progress1);
        btn_download1 = (Button) findViewById(R.id.btn_download1);
        tv_file_name1.setText("微信");

        tv_file_name2 = (TextView) findViewById(R.id.tv_file_name2);
        tv_progress2 = (TextView) findViewById(R.id.tv_progress2);
        pb_progress2 = (ProgressBar) findViewById(R.id.pb_progress2);
        btn_download2 = (Button) findViewById(R.id.btn_download2);
        tv_file_name2.setText("qq");

        btn_download_all = (Button) findViewById(R.id.btn_download_all);

    }

    /**
     * 下载或暂停下载
     *
     * @param view
     */
    public void downloadOrPause(View view) {
        switch (view.getId()) {
            case R.id.btn_download1:
                if (!mDownloadManager.isDownloading(wechatUrl)) {
                    mDownloadManager.download(wechatUrl);
                    btn_download1.setText("暂停");

                } else {
                    btn_download1.setText("下载");
                    mDownloadManager.pause(wechatUrl);
                }
                break;
            case R.id.btn_download2:
                if (!mDownloadManager.isDownloading(qqUrl)) {
                    mDownloadManager.download(qqUrl);
                    btn_download2.setText("暂停");
                } else {
                    btn_download2.setText("下载");
                    mDownloadManager.pause(qqUrl);
                }
                break;
        }
    }

    public void downloadOrPauseAll(View view) {
        if (!mDownloadManager.isDownloading(wechatUrl, qqUrl)) {
            btn_download1.setText("暂停");
            btn_download2.setText("暂停");
            btn_download_all.setText("全部暂停");
            mDownloadManager.download(wechatUrl, qqUrl);//最好传入个String[]数组进去
        } else {
            mDownloadManager.pause(wechatUrl, qqUrl);
            btn_download1.setText("下载");
            btn_download2.setText("下载");
            btn_download_all.setText("全部下载");
        }
    }

    /**
     * 取消下载
     *
     * @param view
     */
    public void cancel(View view) {

        switch (view.getId()) {
            case R.id.btn_cancel1:
                mDownloadManager.cancel(wechatUrl);
                break;
            case R.id.btn_cancel2:
                mDownloadManager.cancel(qqUrl);
                break;
        }
    }

    public void cancelAll(View view) {
        mDownloadManager.cancel(wechatUrl, qqUrl);
        btn_download1.setText("下载");
        btn_download2.setText("下载");
        btn_download_all.setText("全部下载");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if (!checkPermission(permission)) {//针对android6.0动态检测申请权限
            if (shouldShowRationale(permission)) {
                showMessage("需要权限跑demo哦...");
            }
            ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 显示提示消息
     *
     * @param msg
     */
    private void showMessage(String msg) {
        Toast.makeText(DownLoadActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测用户权限
     *
     * @param permission
     * @return
     */
    protected boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否需要显示请求权限的理由
     *
     * @param permission
     * @return
     */
    protected boolean shouldShowRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }


}
