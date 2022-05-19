package com.efrain.intercamprueba.ui.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.efrain.intercamprueba.viewmodels.MainViewModel

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding as VB
    protected lateinit var navController: NavController

    protected val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
        onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected abstract fun onCreate()

    protected abstract fun getViewBinding(): VB

}