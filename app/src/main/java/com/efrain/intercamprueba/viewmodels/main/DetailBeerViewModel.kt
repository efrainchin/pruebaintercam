package com.efrain.intercamprueba.viewmodels.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efrain.intercamprueba.entities.EntityDetailBeer
import com.efrain.intercamprueba.network.repository.BeerRepository
import com.efrain.intercamprueba.viewmodels.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailBeerViewModel @ViewModelInject constructor(private val repository: BeerRepository): BaseViewModel() {

    private val _beers = MutableLiveData<EntityDetailBeer>()
    val beers: LiveData<EntityDetailBeer> get() = _beers

    fun detailBeer(id: String) {
        _loading.value = true
        viewModelScope.launch {
            val response =  repository.detailBeer(id)
            if(response.isSuccessful) {
                response.body()?.let {
                    _beers.value = it.firstOrNull()
                }
                _loading.value = false
            } else {
                _error.value = response.message()
                _loading.value = false
            }
        }
    }

}