package com.treewall.av.pizzaapp.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.treewall.av.pizzaapp.databinding.FragmentWelcomeBinding
import com.treewall.av.pizzaapp.presentation.base.BaseFragment

class WelcomeFragment : BaseFragment<WelcomeViewModel>(WelcomeViewModel::class) {
    lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

}