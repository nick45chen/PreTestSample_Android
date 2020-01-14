package com.example.fuglepretestsample.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuglepretestsample.models.Stock
import com.example.fuglepretestsample.repositories.StocksRepository

class MainViewModel : ViewModel() {

    val isLoadingLiveData: LiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val stocksLiveData: LiveData<List<Stock>> by lazy { MutableLiveData<List<Stock>>() }

    private lateinit var repository: StocksRepository

    fun injectRepository(repository: StocksRepository) {
        this.repository = repository
    }

    fun requestApi() {

    }
}