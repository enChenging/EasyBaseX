package com.release.simplex.ui.act;

import com.release.easybasex.base.BaseWebView;

/**
 * @author Mr.release
 * @create 2020/5/7
 * @Describe
 */

public class WebViewPlusActivity extends BaseWebView {

    @Override
    public String getUrl() {
        return "https://www.baidu.com";
    }

    @Override
    public String getActivityTitle() {
        return "WebViewPlus";
    }
}
