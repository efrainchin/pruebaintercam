package com.efrain.intercamprueba.viewmodels.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    protected val _loading = MutableLiveData<Boolean>()
    protected val _error = MutableLiveData<String>()

    val loading: LiveData<Boolean> get() = _loading
    val error: LiveData<String> get() = _error
}