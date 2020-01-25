package com.treewall.av.pizzaapp.presentation.base.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class ListViewHolder<T : ListItem>(
    val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    var data: T? = null
        private set

    fun bindData(data: T) {
        this.data = data
        bind(data)
    }

    protected abstract fun bind(data: T)

}
