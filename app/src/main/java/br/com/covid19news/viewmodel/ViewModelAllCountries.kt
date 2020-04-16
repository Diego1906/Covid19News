package br.com.covid19news.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.covid19news.domain.DataStatisticsModel
import br.com.covid19news.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelAllCountries(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    private val _data: MutableLiveData<DataStatisticsModel> = MutableLiveData<DataStatisticsModel>()
    val data: MutableLiveData<DataStatisticsModel>
        get() = _data

    private val _toast = MutableLiveData<String>()
    val toast: MutableLiveData<String>
        get() = _toast

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun onShowData(typeSearch: String) {
        viewModelScope.launch {
            onCallRepository(typeSearch)
        }
    }

    private suspend fun onCallRepository(typeSearch: String) {
        withContext(Dispatchers.IO) {
            try {
                _data.postValue(
                    repository.getDataAllCountries(typeSearch)
                )
            } catch (ex: Throwable) {
                _toast.postValue(ex.message)
            }
        }
    }
}