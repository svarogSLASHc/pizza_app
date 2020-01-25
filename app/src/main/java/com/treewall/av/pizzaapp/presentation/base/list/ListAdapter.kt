package com.treewall.av.pizzaapp.presentation.base.list


import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class ListAdapter<T : ListItem> : RecyclerView.Adapter<ListViewHolder<T>>() {
    override fun getItemCount() = items.size

    private var items: MutableList<T> = ArrayList()
    val delegateManager: DelegateManager<T> = DelegateManager(this)

    fun getItems(): List<T> {
        return items
    }

    fun setItems(data: List<T>) {
        items = data.toMutableList()
        notifyDataSetChanged()
    }

    fun setItemAt(item: T, position: Int) {

        if (position < 0 || position >= items.size) {
            Log.d(TAG, "position was " + position + " but array length was only " + items.size)
            throw IllegalArgumentException("Position was " + position + " but array length was only " + items.size)
        }
        items[position] = item
        notifyItemChanged(position)
    }

    fun addItem(item: T) {

        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItemAt(item: T, position: Int) {

        if (position < 0 || position >= items.size) {
            Log.d(TAG, "position was " + position + " but array length was only " + items.size)
            throw IllegalArgumentException("Position was " + position + " but array length was only " + items.size)
        }
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun getItemAt(position: Int): T {
        return if (position < items.size && position >= 0) {
            items[position]
        } else {
            throw IllegalArgumentException("Item position should be from 0 to items size")
        }
    }

    fun removeItem(item: T) {
        val position = items.indexOf(item)
        items.removeAt(position)
        notifyItemRemoved(position)
        if (position >= 0 && position < items.size) {
            items.removeAt(position)
        }
    }

    /**
     * removeItemFrom - romoves item from adapter at specific position
     *
     * @param position
     */
    fun removeItemAt(position: Int) {
        if (position >= 0 && position < items.size) {
            items.removeAt(position)
            notifyItemRangeChanged(position, itemCount)
        } else {
            throw IllegalArgumentException("Item position should be from 0 to items size")
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder<T>, position: Int) {
        delegateManager.onBindViewHolder(holder, items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder<T> {
        return delegateManager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return delegateManager.getViewType(items[position])
    }

    override fun onViewDetachedFromWindow(holder: ListViewHolder<T>) {
        super.onViewDetachedFromWindow(holder)
        // remove any animation
        holder.itemView.clearAnimation()
    }

    companion object {
        private val TAG = ListAdapter::class.java.canonicalName
    }
}
