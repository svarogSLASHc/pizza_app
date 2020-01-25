package com.treewall.av.pizzaapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.treewall.av.pizzaapp.databinding.FragmentLoginBinding
import com.treewall.av.pizzaapp.presentation.base.BaseFragment

class LoginFragment : BaseFragment<LoginViewModel>(LoginViewModel::class) {
    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorMessage.observe(viewLifecycleOwner,
            Observer { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() })
    }
}