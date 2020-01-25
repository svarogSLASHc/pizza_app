package com.treewall.av.pizzaapp.presentation.login

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso


@BindingAdapter("app:errorRes")
fun setError(inputLayout: TextInputLayout, msg: Int?) {
    inputLayout.error = msg?.let { inputLayout.context.getString(it) }
}

@BindingAdapter("app:setTestRes")
fun setTestRes(textView: TextView, @StringRes textRes: Int?) {
    textView.text = textRes?.let {
        textView.context.getString(textRes)
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Picasso.with(view.context)
        .load(imageUrl)
        .placeholder(com.treewall.av.pizzaapp.R.drawable.ic_pizza_main)
        .into(view)
}