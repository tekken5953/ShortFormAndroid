package com.example.shortformandroid.util

import android.os.SystemClock
import android.view.View

abstract class OnAdapterItemSingleClick : OnAdapterItemClick {
    private var mLastClickTime: Long = 0
    private val minClickInterval: Long = 1000

    abstract fun onSingleClick(v: View?, position: Int)

    override fun onItemClick(v: View, position: Int) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        // 중복클릭 아닌 경우
        if (elapsedTime > minClickInterval) onSingleClick(v, position)
//        else { ToastUtils(BaseApplication.appContext).showMessage("잠시 후에 시도해주세요") }
    }
}