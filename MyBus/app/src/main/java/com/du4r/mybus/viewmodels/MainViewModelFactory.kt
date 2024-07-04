package com.du4r.mybus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.du4r.mybus.service.RetrofitService

class MainViewModelFactory(private val retrofitService: RetrofitService) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(retrofitService) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}