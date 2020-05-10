package br.com.covid19news.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.domain.DataStatisticsModel
import br.com.covid19news.domain.ResponseModel
import br.com.covid19news.repository.IRepository
import br.com.covid19news.util.TypeSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val INDEX_ZERO = 0

class GenericViewModel(val repository: IRepository, application: Application) :
    AndroidViewModel(application), IGenericViewModel {

    private val TAG = javaClass.simpleName

    private val _data = MutableLiveData<DataStatisticsModel>()
    val data
        get() = _data

    private val _response = MutableLiveData<ResponseModel>()
    val response
        get() = _response

    private val _toast = MutableLiveData<String?>()
    val toast
        get() = _toast

    private val _isVisibleProgressBar = MutableLiveData(View.GONE)
    val isVisibleProgressBar
        get() = _isVisibleProgressBar

    private val _swipeIsRefreshing = MutableLiveData<Boolean>()
    val swipeIsRefreshing
        get() = _swipeIsRefreshing

    private val _isVisibleCardViewItem = MutableLiveData(View.GONE)
    val isVisibleCardViewItem
        get() = _isVisibleCardViewItem

    private val _isShowData = MutableLiveData(true)
    val isShowData
        get() = _isShowData

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    override fun onShowData(filter: String?, typeSearch: TypeSearch) {
        viewModelScope.launch {
            onShowProgressBar(true)
            onCallRepository(filter, typeSearch)
            onIsVisibleCardViewItem(true)
            onIsShowData(false)
            onShowProgressBar(false)
        }
    }

    private suspend fun onCallRepository(filter: String?, typeSearch: TypeSearch) {
        withContext(Dispatchers.IO) {
            try {
                _data.postValue(
                    onChooseOptionSearch(filter, typeSearch)
                )
            } catch (ex: Throwable) {
                Timber.tag(TAG)
                Timber.e(ex)
                onShowToast(App.getContext().getString(R.string.msg_error, ex.message))
            }
        }
    }

    private suspend fun onChooseOptionSearch(filter: String?, typeSearch: TypeSearch) =
        when (typeSearch) {
            TypeSearch.All, TypeSearch.Country -> {
                filter?.let {
                    repository.getStatusWorldOrByCountry(it)
                }
            }
            TypeSearch.Statistcs -> {
                repository.getStatusAllCountries()
            }
        }

    override fun onSetResponse(data: DataStatisticsModel) {
        _response.value = data.listResponse?.get(INDEX_ZERO)
    }

    private fun onShowProgressBar(value: Boolean) {
        _isVisibleProgressBar.value = onIsVisible(value)
    }

    override fun onShowToast(value: String?) {
        _toast.postValue(value)
    }

    override fun onHideSwipeRefresh() {
        _swipeIsRefreshing.value = false
    }

    override fun onIsVisibleCardViewItem(value: Boolean) {
        _isVisibleCardViewItem.value = onIsVisible(value)
    }

    private fun onIsVisible(value: Boolean) = when (value) {
        true -> View.VISIBLE
        else -> View.GONE
    }

    private fun onIsShowData(value: Boolean) {
        _isShowData.value = value
    }
}