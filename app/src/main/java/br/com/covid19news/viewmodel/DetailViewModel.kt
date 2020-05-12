package br.com.covid19news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.covid19news.domain.ResponseDomainModel

class DetailViewModel(response: ResponseDomainModel) : ViewModel() {

    private val _response = MutableLiveData<ResponseDomainModel>()
    val response
        get() = _response

    init {
        _response.value = response
    }
}