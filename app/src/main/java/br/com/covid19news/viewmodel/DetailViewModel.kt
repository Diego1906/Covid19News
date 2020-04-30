package br.com.covid19news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.covid19news.domain.ResponseModel

class DetailViewModel(response: ResponseModel) : ViewModel() {

    private val _response = MutableLiveData<ResponseModel>()
    val response
        get() = _response

    init {
        _response.value = response
    }
}