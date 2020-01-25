package com.treewall.av.pizzaapp.presentation.product_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.authorization.GetSelectedDistributor
import com.treewall.av.pizzaapp.domain.pizza.GetProductsByDistributorIdUseCase
import com.treewall.av.pizzaapp.domain.pizza.entity.ProductRequest
import com.treewall.av.pizzaapp.presentation.base.BackClickListener
import com.treewall.av.pizzaapp.presentation.base.BaseViewModel
import com.treewall.av.pizzaapp.presentation.product_list.list.ProductListItem
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getProducts: GetProductsByDistributorIdUseCase,
    private val getSelectedDistributor: GetSelectedDistributor
) : BaseViewModel(), BackClickListener {
    private val _productList = MutableLiveData<List<ProductListItem>>()
    val productList: LiveData<List<ProductListItem>>
        get() = _productList

    init {
        viewModelScope.launch {
            getDistributorId()?.apply {
                fetchProducts(this)?.let(_productList::postValue)
            }
        }
    }

    override fun onCLick() {
        navigateBack()
    }

    private suspend fun getDistributorId() =
        when (val selectedDistributor = getSelectedDistributor()) {
            is Result.Success -> selectedDistributor.data.distributor.id.toInt()
            else -> null
        }

    private suspend fun fetchProducts(distributorId: Int) =
        when (val products = getProducts(ProductRequest(distributorId))) {
            is Result.Success -> products.data.products
                .map {
                    ProductListItem(
                        it.id,
                        it.price.replace(".", ",") + "€",
                        R.string.product_type_pizza,
                        it.name,
                        it.img
                    ) { id -> itemClicked(id) }
                }
            else -> null
        }

    private fun itemClicked(productId: String) {
        productList.value
            ?.firstOrNull { it -> it.id == productId }
    }
}