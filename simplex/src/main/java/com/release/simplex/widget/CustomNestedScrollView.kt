package com.release.simplex.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

/**
 * 禁止拦截子view 的滑动事件
 * @author yancheng
 * @since 2021/12/20
 */
class CustomNestedScrollView(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {
    private var scrollable = true

    constructor(context: Context) : this(context, null)

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return scrollable && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return scrollable
    }

    fun setScrollingEnabled(enabled: Boolean) {
        scrollable = enabled
    }
}