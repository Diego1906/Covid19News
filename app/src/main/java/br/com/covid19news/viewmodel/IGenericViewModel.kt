package br.com.covid19news.viewmodel

import br.com.covid19news.util.TypeSearch

interface IGenericViewModel : IBaseViewModel {

    fun onShowData(filter: String?, typeSearch: TypeSearch)
    fun onHideSwipeRefresh()
}