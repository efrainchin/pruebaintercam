package com.efrain.intercamprueba.viewmodels.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efrain.intercamprueba.entities.EntityBeers
import com.efrain.intercamprueba.network.repository.BeerRepository
import com.efrain.intercamprueba.viewmodels.base.BaseViewModel
import kotlinx.coroutines.launch

class BeersViewModel @ViewModelInject constructor(private val repository: BeerRepository): BaseViewModel() {

    private val _beers = MutableLiveData<List<EntityBeers>>()
    val beers: LiveData<List<EntityBeers>> get() = _beers

    fun getAllBeersPage(page: String) {
        _loading.value = true
        viewModelScope.launch {
            val response =  repository.getAllBeers(page)
            if(response.isSuccessful) {
                response.body()?.let {
                    _beers.value = it
                }
                _loading.value = false
            } else {
                _error.value = response.message()
                _loading.value = false
            }
        }
    }

}