package br.com.covid19news.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import br.com.covid19news.domain.*
import br.com.covid19news.remote.Service
import br.com.covid19news.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EntireWorldViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository: Repository = Repository(Service())

    private val _data = MutableLiveData<DataStatisticsModel>()
    val data
        get() = _data

    private val _toast = MutableLiveData<String>()
    val toast
        get() = _toast

    private val _progress = MutableLiveData(View.GONE)
    val progress
        get() = _progress

    private val _cases = MutableLiveData<CasesModel>()
    val cases
        get() = _cases

    private val _deaths = MutableLiveData<DeathsModel>()
    val deaths
        get() = _deaths

    private val _tests = MutableLiveData<TestsModel>()
    val tests
        get() = _tests

    private val _response = MutableLiveData<ResponseModel>()
    val response
        get() = _response

    private val _country = MutableLiveData<String>()
    val country
        get() = _country

    private val _day = MutableLiveData<String>()
    val day
        get() = _day

    private val _time = MutableLiveData<String>()
    val time
        get() = _time

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
                Timber.tag(javaClass.simpleName);
                Timber.e(ex)
                _toast.postValue(ex.message)
            }
        }
    }

    fun onSortData(data: DataStatisticsModel) {
        _response.value = data.response?.get(0)
        _cases.value = _response.value?.cases
        _deaths.value = _response.value?.deaths
        _tests.value = _response.value?.tests
        _country.value = _response.value?.country
        _day.value = _response.value?.day
        _time.value = _response.value?.time
    }

    fun onShowProgress(value: Boolean): Int {
        return when (value) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }


    /**
     * Factory for constructing EntireWorldViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EntireWorldViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EntireWorldViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}