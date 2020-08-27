package com.release.easybasex.base;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.release.easybasex.R;
import com.release.easybasex.widget.CoolIndicatorLayout;
import com.release.easybasex.widget.WebLayout;

/**
 * @author Mr.release
 * @create 2020/5/7
 * @Describe
 */
public abstract class BaseWebView extends BaseActivity {
    protected LinearLayout mContainer;

    protected String mUrl;
    protected AgentWeb mAgentWeb;


    @Override
    public int getLayoutId() {
        return R.layout.cyc_activity_web_view;
    }

    @Override
    public void initView() {
        mContainer = (LinearLayout) findViewById(R.id.container);

        if (!StringUtils.isEmpty(getWebUrl())) {
            mUrl = getWebUrl();
        } else {
            ToastUtils.showShort("未获取到url地址");
            finish();
        }

        mTopBar.setTitle(getString(R.string.loading)).postDelayed(new Runnable() {
            @Override
            public void run() {
                mTopBar.setTitle(getWebTitle()).setTitleSelected(true);
            }
        }, 2000);

        mTopBar.setOnBackhtClickListener(v -> {
            // true表示AgentWeb处理了该事件
            if (!mAgentWeb.back()) {
                finish();
            }
        });

        initWebView();
    }


    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContainer, new LinearLayout.LayoutParams(-1, -1))
                .setCustomIndicator(new CoolIndicatorLayout(this))
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mTopBar.setTitle(title);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    /**
     * 设置url地址
     *
     * @return
     */
    public abstract String getWebUrl();

    /**
     * 设置标题
     *
     * @return
     */
    public String getWebTitle() {
        return "";
    }

}
