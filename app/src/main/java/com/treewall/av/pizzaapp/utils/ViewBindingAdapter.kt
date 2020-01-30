package com.treewall.av.pizzaapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import com.treewall.av.pizzaapp.presentation.base.list.ListAdapter
import com.treewall.av.pizzaapp.presentation.base.list.ListItem


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

@BindingAdapter("data")
fun <T : ListItem> setRecyclerViewProperties(recyclerView: RecyclerView, data: List<T>?) {
    if (recyclerView.adapter is ListAdapter<*>) {
        data?.apply {
            (recyclerView.adapter as ListAdapter<T>).setItems(this)
        }
    }
}
