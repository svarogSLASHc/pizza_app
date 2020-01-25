package com.treewall.av.pizzaapp.presentation.product_list.list

import androidx.databinding.ViewDataBinding
import com.treewall.av.pizzaapp.BR
import com.treewall.av.pizzaapp.presentation.base.list.ListViewHolder

class ProductViewHolder(binding: ViewDataBinding) :
    ListViewHolder<ProductListItem>(binding) {

    override fun bind(data: ProductListItem) {
        binding.setVariable(BR.viewModel, data)
    }
}
