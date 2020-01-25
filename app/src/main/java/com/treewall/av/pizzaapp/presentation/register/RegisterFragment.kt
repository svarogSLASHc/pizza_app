package com.treewall.av.pizzaapp.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.treewall.av.pizzaapp.databinding.FragmentRegisterBinding
import com.treewall.av.pizzaapp.presentation.base.BaseFragment

class RegisterFragment : BaseFragment<RegisterViewModel>(RegisterViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentRegisterBinding.inflate(inflater, container, false).apply {
            viewModel = this@RegisterFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }
}