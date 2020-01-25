package com.treewall.av.pizzaapp.presentation.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.treewall.av.pizzaapp.databinding.FragmentProductListBinding
import com.treewall.av.pizzaapp.presentation.base.BaseFragment
import com.treewall.av.pizzaapp.presentation.product_list.list.CenterLayout
import com.treewall.av.pizzaapp.presentation.product_list.list.CirclePagerIndicatorDecoration
import com.treewall.av.pizzaapp.presentation.product_list.list.ProductAdapter

class ProductListFragment : BaseFragment<ProductListViewModel>(ProductListViewModel::class) {
    private lateinit var binding: FragmentProductListBinding
    private val productAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
            .also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val snapHelper = LinearSnapHelper()
        binding.productList.apply {
            adapter = productAdapter
            layoutManager = CenterLayout(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            snapHelper.attachToRecyclerView(this)
            addItemDecoration(CirclePagerIndicatorDecoration())
        }
        viewModel.productList.observe(
            viewLifecycleOwner,
            Observer { data ->
                productAdapter.setItems(data)
            })
    }
}