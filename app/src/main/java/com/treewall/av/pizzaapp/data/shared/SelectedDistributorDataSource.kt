package com.treewall.av.pizzaapp.data.shared

import com.treewall.av.pizzaapp.data.Result
import com.treewall.av.pizzaapp.domain.SelectedDistributor

interface SelectedDistributorDataSource {
    fun save(distributor: SelectedDistributor)

    fun get(): Result<SelectedDistributor>
}

class SelectedDistributorDataSourceImpl : SelectedDistributorDataSource {
    private var distributor: SelectedDistributor? = null

    override fun save(distributor: SelectedDistributor) {
        this.distributor = distributor
    }

    override fun get() =
        if (distributor == null) {
            Result.Empty
        } else {
            Result.Success(distributor!!)
        }
}