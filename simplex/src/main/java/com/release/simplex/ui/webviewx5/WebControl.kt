package com.release.simplex.ui.webviewx5

import android.content.Context
import android.webkit.JavascriptInterface

/**
 * 与js交互
 * @author yancheng
 * @since 2021/7/29
 *
 */
class WebControl(private val context: Context, private val webView: X5WebView) {
    private val mContext = context

    /**
     *  弹toast
     * @param msg String 传递过来的值
     */
    @JavascriptInterface
    fun toastMessage(msg: String) {

    }

    /**
     * 获取js传过来的数据
     * @param str String 传递过来的值
     * @return String
     */
    @JavascriptInterface
    fun getMessage(str: String): String = str
}