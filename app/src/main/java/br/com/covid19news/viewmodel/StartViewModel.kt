package br.com.covid19news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {

    private val _toast = MutableLiveData<String?>()
    val toast
        get() = _toast

    fun onShowToast(value: String?) {
        _toast.value = value
    }
}