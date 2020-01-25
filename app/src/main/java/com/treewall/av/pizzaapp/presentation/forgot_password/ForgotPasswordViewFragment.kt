package com.treewall.av.pizzaapp.presentation.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.treewall.av.pizzaapp.R
import com.treewall.av.pizzaapp.databinding.FragmentForgotBinding
import com.treewall.av.pizzaapp.presentation.base.BaseFragment

class ForgotPasswordViewFragment :
    BaseFragment<ForgotPasswordViewModel>(ForgotPasswordViewModel::class) {

    lateinit var binding: FragmentForgotBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToDialog()
        subscribeToError()
    }

    private fun subscribeToDialog() {
        viewModel.showDialog.observe(viewLifecycleOwner,
            Observer { show -> if (show) showDialog() })
    }

    private fun subscribeToError() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun showDialog() {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.forgot_dialog_title)
            .setMessage(R.string.forgot_dialog_text)
            .setPositiveButton(R.string.forgot_btn_text) { dialog, which ->

            }
            .show()
    }
}