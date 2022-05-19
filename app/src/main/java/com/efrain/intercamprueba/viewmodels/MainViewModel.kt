package com.efrain.intercamprueba.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _mainTitle = MutableLiveData("")
    val mainTitle: LiveData<String> get() = _mainTitle

    fun setMainTitle(title: String?) {
        _mainTitle.value = title
    }

}