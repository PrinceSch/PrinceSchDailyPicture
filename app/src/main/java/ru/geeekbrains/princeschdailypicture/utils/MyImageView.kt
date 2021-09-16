package ru.geeekbrains.princeschdailypicture.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class MyImageView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr:Int=0)
    :AppCompatImageView(context,attr,defStyleAttr){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}