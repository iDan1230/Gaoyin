package com.idan.frame.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.idan.frame.R
import com.idan.frame.databinding.ToolbarLayoutBinding

class Titilebar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
       val db:ToolbarLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.toolbar_layout,this,true)
        db.tb ="titleBar"
//        addView(db.clToolbar)
    }
}