package com.treewall.av.pizzaapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.treewall.av.pizzaapp.domain.authorization.logger.BaseLogger
import com.treewall.av.pizzaapp.presentation.map.MapViewFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    private val logger: BaseLogger by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MapViewFragment.REQUEST_CHECK_SETTINGS) {
            logger.d("Location settings onActivityResult(${resultCode == Activity.RESULT_OK})")
            viewModel.setLocationSettingsEnabled(resultCode == Activity.RESULT_OK)
        }
    }
}
