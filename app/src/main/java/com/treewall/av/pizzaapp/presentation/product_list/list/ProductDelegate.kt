package com.treewall.av.pizzaapp.presentation.product_list.list

import android.view.ViewGroup
import com.treewall.av.pizzaapp.databinding.ItemProductBinding
import com.treewall.av.pizzaapp.presentation.base.list.ListAdapter
import com.treewall.av.pizzaapp.presentation.base.list.ListAdapterDelegate
import com.treewall.av.pizzaapp.presentation.base.list.ListItem
import com.treewall.av.pizzaapp.presentation.base.list.ListViewHolder
import com.treewall.av.pizzaapp.utils.getLayoutInflater

class ProductDelegate : ListAdapterDelegate<ProductListItem>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        adapter: ListAdapter<ProductListItem>
    ): ListViewHolder<ProductListItem> {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                parent.getLayoutInflater(),
                parent,
                false
            )
        )
    }

    override fun isDelegateForDataType(data: ListItem) = data is ProductListItem
}