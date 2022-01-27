package com.release.simplex.ui.act

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.release.simplex.R
import com.release.simplex.ui.webviewx5.BaseWebActivity
import com.release.simplex.ui.webviewx5.WebControl
import com.release.simplex.ui.webviewx5.X5WebView
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view_x5.*

/**
 *
 * @author yancheng
 * @since 2022/1/27
 */
class WebViewX5Activity : BaseWebActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_x5)
        initWebView()
    }

    private fun initWebView() {
        //初始化webview
        mWebView = X5WebView(this@WebViewX5Activity){
            Log.i("cyc", "initWebView height: $it")
        }
        mWebView?.let {
            it.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.addJavascriptInterface(
                WebControl(this@WebViewX5Activity, it),
                "webControl"
            )
            vContainerFl.addView(it)

            it.loadUrl("https://www.baidu.com")
//            it.loadUrl("http://124.71.178.170:15672/doc/preview?url=https://teacher-woker.obs.cn-east-3.myhuaweicloud.com/doc/example/sample.docx&type=mobile")
        }

    }
}