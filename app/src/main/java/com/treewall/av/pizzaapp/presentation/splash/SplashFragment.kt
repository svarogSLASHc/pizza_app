package com.treewall.av.pizzaapp.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.treewall.av.pizzaapp.databinding.FragmentSplashBinding
import com.treewall.av.pizzaapp.presentation.base.BaseFragment

class SplashFragment : BaseFragment<SplashViewModel>(SplashViewModel::class) {
    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onCreate()
    }
}