package com.release.simplex.ui.webviewx5

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

/**
 * WebViewActivity基类
 *
 * @author yancheng
 * @since 2021/9/1
 */
abstract class BaseWebActivity : AppCompatActivity() {

    protected var mWebView: X5WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideTheBottomNavigationBar()
    }


    override fun onDestroy() {
        super.onDestroy()
        mWebView?.run {
            //加载null内容
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            //清除历史记录
            clearHistory()
            clearCache(true)
            clearFormData()
            //移除WebView
            (parent as ViewGroup).removeView(mWebView)
            destroy()
            mWebView = null
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && mWebView?.canGoBack() == true) {
            //返回键监听 回滚H5页面
            mWebView?.goBack()
            false
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    /**
     * 返回或关闭当前页，如果当前Web页面能够返回上一级就返回上一级，否则就关闭页面
     */
    private fun backOrFinish() {
        if (mWebView?.canGoBack() == true) {
            mWebView?.goBack()
        } else {
            onBack()
        }
    }

    protected open fun onBack() {
        finish()
    }

    /**
     * 隐藏底部导航栏
     * @return Unit
     */
    protected open fun hideTheBottomNavigationBar() {
        // 隐藏底部按键
        val params = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }
}