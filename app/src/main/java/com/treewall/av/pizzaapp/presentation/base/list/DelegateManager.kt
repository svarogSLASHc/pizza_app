package com.treewall.av.pizzaapp.presentation.base.list

import android.view.ViewGroup
import java.util.*


class DelegateManager<T : ListItem>(private val adapter: ListAdapter<T>) {
    private val delegates = ArrayList<AdapterDelegate<T>>()

    fun addDelegatem(delegate: AdapterDelegate<T>): DelegateManager<*> {
        delegates.add(delegate)
        return this
    }

    fun <M : AdapterDelegate<T>> addDelegate(delegate: M): DelegateManager<*> {
        delegates.add(delegate)
        return this
    }

    fun addDelegates(position: Int, delegate: AdapterDelegate<T>): DelegateManager<*> {
        delegates.add(position, delegate)
        return this
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder<T> {
        return delegates[viewType].onCreateViewHolder(parent, adapter)
    }

    fun onBindViewHolder(viewHolder: ListViewHolder<T>, data: T) {
        val viewType = viewHolder.itemViewType
        delegates[viewType].onBindViewHolder(viewHolder, data)
    }

    fun getViewType(data: ListItem): Int {
        return delegates.indexOfFirst { it.isDelegateForDataType(data) }
    }
}
