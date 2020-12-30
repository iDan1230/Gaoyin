package com.idan.frame.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.idan.frame.R
import com.idan.frame.databinding.ToolbarLayoutBinding
import com.idan.frame.ktx.show

class Titlebar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var mDB: ToolbarLayoutBinding? = null
    private var title: String? = null
    private var tvTitle: TextView? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Titlebar)
        title = typedArray.getString(R.styleable.Titlebar_title)
//        typedArray.recycle()
//        val db: ToolbarLayoutBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),
//            R.layout.toolbar_layout,
//            this,
//            true
//        )
//        mDB = db
//
        View.inflate(context, R.layout.toolbar_layout, this)
        tvTitle = findViewById(R.id.tv_title)
        tvTitle?.text = title
    }

    fun setTitle(title: String) {
        "$title TitleBar---->".show()
        tvTitle?.text = title
    }
}