package com.treewall.av.pizzaapp.presentation.product_list.list

import com.treewall.av.pizzaapp.presentation.base.list.AdapterDelegate
import com.treewall.av.pizzaapp.presentation.base.list.ListAdapter
import com.treewall.av.pizzaapp.presentation.base.list.ListItem

class ProductAdapter : ListAdapter<ListItem>() {
    init {
        delegateManager.addDelegate(ProductDelegate() as AdapterDelegate<ListItem>)
    }
}