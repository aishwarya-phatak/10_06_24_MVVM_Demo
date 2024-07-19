package com.bitcode.a10_06_24_mvvm_demo.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bitcode.a10_06_24_mvvm_demo.R
import com.bumptech.glide.Glide

@BindingAdapter("image_url")
fun loadImageUrlToImageView(imageView: ImageView,value : String){
    Glide.with(imageView)
        .load(value)
        .circleCrop()
        .error(R.mipmap.ic_launcher)
        .placeholder(R.mipmap.ic_launcher)
        .into(imageView)
}