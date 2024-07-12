package com.example.shortformandroid.adapter

import android.view.View
import androidx.viewpager.widget.ViewPager

class BookPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width

        when {
            position < -1 -> {
                page.alpha = 0f
            }
            position <= 1 -> {
                page.alpha = 1f
                page.translationX = -position * pageWidth

                val scaleFactor = Math.max(0.85f, 1 - Math.abs(position))
                val verticalMargin = page.height * (1 - scaleFactor) / 2
                val horizontalMargin = pageWidth * (1 - scaleFactor) / 2

                page.translationX = if (position < 0) {
                    horizontalMargin - verticalMargin / 2
                } else {
                    -horizontalMargin + verticalMargin / 2
                }

                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
            else -> {
                page.alpha = 0f
            }
        }
    }
}