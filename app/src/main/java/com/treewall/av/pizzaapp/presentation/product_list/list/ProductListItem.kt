package com.treewall.av.pizzaapp.presentation.product_list.list

import androidx.annotation.StringRes
import com.treewall.av.pizzaapp.presentation.base.list.ListItem

data class ProductListItem(
    val id: String,
    val price: String,
    @StringRes val category: Int,
    val name: String,
    val img: String,
    val buy: (id: String) -> Unit
) : ListItem