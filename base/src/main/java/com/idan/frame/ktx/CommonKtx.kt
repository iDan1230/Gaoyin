package com.idan.frame.ktx

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.idan.frame.widget.Titlebar

@BindingAdapter("srcCompat")
fun ImageAdapter(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}

@BindingAdapter("title")
fun titleBarTitle(titleBar: Titlebar, title: String) {
    titleBar.setTitle(title)
}