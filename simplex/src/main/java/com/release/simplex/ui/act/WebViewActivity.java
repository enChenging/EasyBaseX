package com.release.simplex.ui.act;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.release.easybasex.base.BaseActivity;
import com.release.simplex.R;
import com.release.simplex.widget.ProgressActivity;

import butterknife.BindView;


/*******
 *
 *网页
 *
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_msg)
    WebView wvMsg;
    @BindView(R.id.progress)
    ProgressActivity mProgress;
    private int typeFinish = 0, typeFinishCache;
    private WebSettings mWebSettings;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        mProgress.showLoading();
//        wvMsg.loadUrl("https://www.baidu.com");
        wvMsg.loadUrl("http://124.71.178.170:15672/doc/preview?url=https://teacher-woker.obs.cn-east-3.myhuaweicloud.com/doc/example/sample.docx&type=embedded");
        initConiftWeb();
    }

    private void initConiftWeb() {

        wvMsg.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
        mWebSettings = wvMsg.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        final String dbPath = getApplicationContext().getDir("db", Context.MODE_PRIVATE).getPath();
        mWebSettings.setDatabasePath(dbPath);
        //自适应屏幕
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        //清理缓存
        wvMsg.clearCache(true);

        wvMsg.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                typeFinishCache = 0;
                super.onPageStarted(view, url, favicon);
            }

            /**
             * 表示是否阻止webview继续加载url，默认值为 false
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.i("shouldOverrideUrlLoading: " + url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                typeFinishCache = typeFinish;
                mProgress.showContent();
                super.onPageFinished(view, url);
            }
        });
        wvMsg.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                }

            }
        });

        wvMsg.addJavascriptInterface(new JsCallAndroid() {
            @JavascriptInterface
            @Override
            public void goCheckByMe() {
            }
        }, "android");

        wvMsg.addJavascriptInterface(new JsCallAndroid2() {
            @JavascriptInterface
            @Override
            public void goLoad(String uri) {
                Logger.i("goLoad-----workid:" + uri);
            }
        }, "androidGoLoad");
    }

    public interface JsCallAndroid {
        void goCheckByMe();
    }

    public interface JsCallAndroid2 {
        void goLoad(String uri);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (typeFinishCache == 1) {
            if (keyCode == KeyEvent.KEYCODE_BACK && wvMsg.canGoBack()) {
                wvMsg.goBack();// 返回前一个页面
                return true;
            }
        } else {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
