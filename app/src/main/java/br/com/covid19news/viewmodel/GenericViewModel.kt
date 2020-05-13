package br.com.covid19news.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.domain.ResponseDomainModel
import br.com.covid19news.repository.IRepository
import br.com.covid19news.util.TypeSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class GenericViewModel(val repository: IRepository, application: Application) :
    AndroidViewModel(application), IBaseViewModel {

    private val TAG = javaClass.simpleName

    val responseList = repository.responses

    val isNotNetworkConnected = repository.isNotNetworkConnected

    private val _response = MutableLiveData<ResponseDomainModel>()
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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun onShowData(params: Triple<Boolean, String?, TypeSearch>) {
        if (params.first)
            onHideSwipeRefresh()

        onFetchData(params.second, params.third)
    }

    private fun onFetchData(filter: String?, typeSearch: TypeSearch) {
        viewModelScope.launch {
            onShowProgressBar(true)
            onCallRepository(filter, typeSearch)
            onShowProgressBar(false)
        }
    }

    private suspend fun onCallRepository(filter: String?, typeSearch: TypeSearch) {
        withContext(Dispatchers.IO) {
            try {
                onChooseOptionSearch(filter, typeSearch)
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
                filter?.let { value ->
                    repository.getStatisticsWorldOrByCountry(value).run {
                        if (this.first) {
                            this.second?.let {
                                _response.postValue(it as? ResponseDomainModel)
                                onShowCardViewItem()
                            }
                        }
                    }
                }
            }
            TypeSearch.Statistcs -> {
                repository.refreshStatisticsAllCountries()
            }
        }

    private fun onShowProgressBar(value: Boolean) {
        _isVisibleProgressBar.value = onIsVisible(value)
    }

    override fun onShowToast(value: String?) {
        _toast.postValue(value)
    }

    private fun onHideSwipeRefresh() {
        _swipeIsRefreshing.value = false
    }

    private fun onShowCardViewItem() {
        _isVisibleCardViewItem.postValue(onIsVisible(true))
    }

    private fun onIsVisible(value: Boolean) = when (value) {
        true -> View.VISIBLE
        else -> View.GONE
    }

    fun onIsNotNetworkConnectedComplete() {
        repository.onIsNotConnectedComplete()
    }
}