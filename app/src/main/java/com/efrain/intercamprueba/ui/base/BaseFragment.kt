package com.efrain.intercamprueba.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.efrain.intercamprueba.viewmodels.MainViewModel

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding as VB
    protected lateinit var navController: NavController
    protected lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected abstract fun onViewCreated()

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    private fun initMainViewModel() {
        activity?.run {
            val mainViewModel1: MainViewModel by viewModels()
            mainViewModel = mainViewModel1
        } ?: throw Exception("Invalid Activity")

    }
}