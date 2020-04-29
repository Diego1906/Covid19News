package br.com.covid19news.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.covid19news.domain.*
import br.com.covid19news.repository.IRepository
import br.com.covid19news.util.TypeSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val INDEX_ZERO = 0

class CovidViewModel(val repository: IRepository, application: Application) :
    AndroidViewModel(application) {

    private val _data = MutableLiveData<DataStatisticsModel>()
    val data
        get() = _data

    private val _toast = MutableLiveData<String?>()
    val toast
        get() = _toast

    private val _isVisibleProgressBar = MutableLiveData(View.GONE)
    val isVisibleProgressBar
        get() = _isVisibleProgressBar

    private val _swipeIsRefreshing = MutableLiveData<Boolean>()
    val swipeIsRefreshing
        get() = _swipeIsRefreshing

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

    private val _isVisibleCardView = MutableLiveData(View.INVISIBLE)
    val isVisibleCardView
        get() = _isVisibleCardView

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun onShowData(filter: String, typeSearch: TypeSearch) {
        viewModelScope.launch {
            onShowProgressBar(true)
            onCallRepository(filter, typeSearch)
            onShowProgressBar(false)
        }
    }

    private suspend fun onCallRepository(filter: String, typeSearch: TypeSearch) {
        withContext(Dispatchers.IO) {
            try {
                _data.postValue(
                    //repository.getStatusWorldOrByCountry(filter)
                    onChooseOptionSearch(filter, typeSearch)
                )
            } catch (ex: Throwable) {
                Timber.tag(javaClass.simpleName)
                Timber.e(ex)
                onShowToast(ex.message)
            }
        }
    }


    private suspend fun onChooseOptionSearch(filter: String, typeSearch: TypeSearch) =
        when (typeSearch) {
            TypeSearch.All, TypeSearch.Country -> {
                repository.getStatusWorldOrByCountry(filter)
            }
            TypeSearch.Statistcs -> {
                repository.getStatusAllCountries()
            }
        }

    fun onSetCountryData(data: DataStatisticsModel) {
        _response.value = data.response?.get(INDEX_ZERO)
        _cases.value = _response.value?.cases
        _deaths.value = _response.value?.deaths
        _tests.value = _response.value?.tests
        _country.value = _response.value?.country
        _day.value = _response.value?.day
        _time.value = _response.value?.time
    }

    private fun onShowProgressBar(value: Boolean) {
        _isVisibleProgressBar.postValue(onIsVisible(value))
    }

    fun onShowToast(value: String?) {
        _toast.postValue(value)
    }

    fun onHideSwipeRefresh() {
        _swipeIsRefreshing.value = false
    }

    fun onIsVisibleCardView(value: Boolean) {
        _isVisibleCardView.value = onIsVisible(value)
    }

    private fun onIsVisible(value: Boolean) = when (value) {
        true -> View.VISIBLE
        else -> View.GONE
    }
}