package com.example.fuglepretestsample.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fuglepretestsample.repositories.IStocksRepository
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val repository: IStocksRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(repository) as T
        else
            throw IllegalArgumentException("UnKnow ViewModel class")
    }
}