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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.reflect.KSuspendFunction0

class GenericViewModel(val repository: IRepository, application: Application) :
    AndroidViewModel(application), IBaseViewModel {

    private val TAG = javaClass.simpleName
    private var _filter: String? = null

    val responses = repository.responses
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

    fun onRefreshStatistics() {
        onExecuteCall(true, repository::refreshStatisticsAllCountries)
    }

    fun onGetStatistics(params: Pair<String?, Boolean>) {
        _filter = params.first
        onExecuteCall(params.second, ::getStatisticsWorldOrByCountry)
    }

    private fun onExecuteCall(value: Boolean, block: KSuspendFunction0<Unit>) {
        onCheckHideSwipeRefresh(value)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                onShowProgressBar(true)
                try {
                    block.invoke()
                } catch (ex: Throwable) {
                    onShowErrorMsg(ex)
                } finally {
                    onShowProgressBar(false)
                }
            }
        }
    }

    private fun onCheckHideSwipeRefresh(value: Boolean) {
        if (value)
            onHideSwipeRefresh()
    }

    private suspend fun getStatisticsWorldOrByCountry() {
        _filter?.let { value ->
            repository.getStatisticsWorldOrByCountry(value).run {
                onSetResponse()
            }
        }
    }

    private fun ResponseDomainModel?.onSetResponse() {
        this?.let {
            _response.postValue(it)
            onShowCardViewItem()
        }
    }

    private fun onShowProgressBar(value: Boolean) {
        _isVisibleProgressBar.postValue(onIsVisible(value))
    }

    private fun onShowErrorMsg(ex: Throwable) {
        Timber.tag(TAG)
        Timber.e(ex)
        onShowToast(App.getContext().getString(R.string.msg_error, ex.message))
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