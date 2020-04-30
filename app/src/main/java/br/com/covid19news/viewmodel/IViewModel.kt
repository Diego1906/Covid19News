package br.com.covid19news.viewmodel

import br.com.covid19news.util.TypeSearch

interface IViewModel {
    fun onShowData(filter: String?, typeSearch: TypeSearch)
    fun onShowToast(value: String?)
    fun onHideSwipeRefresh()
}