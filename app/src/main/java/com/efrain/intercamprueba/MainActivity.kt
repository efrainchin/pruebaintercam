package com.efrain.intercamprueba

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.efrain.intercamprueba.databinding.ActivityMainBinding
import com.efrain.intercamprueba.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), NavController.OnDestinationChangedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate() {
        initializeNavigationUI()
        binding.floatingActionButton.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.title_close_app))
                setMessage(getString(R.string.description_close_app))
                setPositiveButton(getString(R.string.text_ok)) { _, _ ->
                    finish()
                }
                setNegativeButton(getString(R.string.btn_cancel)) { _,_ ->

                }
            }.show()
        }
    }

    override fun getViewBinding()
            = ActivityMainBinding.inflate(layoutInflater)

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id == R.id.loginFragment || destination.id == R.id.favoritesFragment) {
            binding.floatingActionButton.visibility = View.GONE
        } else {
            binding.floatingActionButton.visibility = View.VISIBLE
        }

        when(destination.id) {
            R.id.loginFragment -> isEnabledDisplayHome(false)
            R.id.mainFragment -> {
                binding.toolbar.visibility = View.VISIBLE
                supportActionBar?.let {
                    it.setDisplayHomeAsUpEnabled(false)
                    it.setDisplayShowHomeEnabled(false)
                }
            }
            else -> isEnabledDisplayHome(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.loginFragment
            || navController.currentDestination?.id == R.id.mainFragment)
            finish()
        else
            super.onBackPressed()
    }

    private fun isEnabledDisplayHome(status: Boolean) {
        if(status) {
            binding.toolbar.visibility = View.VISIBLE
        } else {
            binding.toolbar.visibility = View.GONE
        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(status)
            it.setDisplayShowHomeEnabled(status)
        }
    }

    private fun initializeNavigationUI() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener(this)
    }
}