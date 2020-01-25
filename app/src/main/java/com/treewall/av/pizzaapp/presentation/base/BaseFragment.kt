package com.treewall.av.pizzaapp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass


open class BaseFragment<VM : BaseViewModel>(clazz: KClass<VM>) : Fragment() {

    val viewModel: VM by viewModel(clazz)
    val navigation: NavController
        get() = this.findNavController()
    val logger: BaseLogger by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnBackPressed()

        setupNavigation()
    }


    fun onBackPressed() {
        val handled = navigation.popBackStack()
        if (!handled) {
            requireActivity().finish()
        }
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
    }

    private fun setupNavigation() {
        viewModel.navigationEvent.observe(viewLifecycleOwner,
            Observer { navEvent ->
                when (navEvent) {
                    is Navigation.NavigationEvent ->
                        navigation.navigate(navEvent.destination)
                    is Navigation.Back ->
                        navigation.popBackStack()
                }
            })
    }
}