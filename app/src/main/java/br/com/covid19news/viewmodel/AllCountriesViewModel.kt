package br.com.covid19news.viewmodel

import android.app.Application
import androidx.lifecycle.*
import br.com.covid19news.domain.DataStatisticsModel
import br.com.covid19news.remote.Service
import br.com.covid19news.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllCountriesViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository: Repository = Repository(Service())

    private val _data = MutableLiveData<DataStatisticsModel>()
    val data
        get() = _data

    private val _toast = MutableLiveData<String>()
    val toast
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

    /**
     * Factory for constructing AllCountriesViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AllCountriesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AllCountriesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}