package com.release.simplex.ui.act;

import com.release.easybasex.base.BaseWebView;

/**
 * @author Mr.release
 * @create 2020/5/7
 * @Describe
 */

public class WebViewPlusActivity extends BaseWebView {

    @Override
    public String getWebTitle() {
        return "WebViewPlus";
    }

    @Override
    public String getWebUrl() {
//        return "https://www.baidu.com";
        return "http://124.71.178.170:15672/doc/preview?url=https://teacher-woker.obs.cn-east-3.myhuaweicloud.com/doc/example/sample.docx&type=embedded";
//        return "http://124.71.178.170:15672/doc/preview?url=https://teacher-woker.obs.cn-east-3.myhuaweicloud.com/uploadfile/2022-01-27/%E5%A4%87%E6%A1%88app%E9%97%AE%E9%A2%98%E6%95%B4%E7%90%86.docx";
    }
}
