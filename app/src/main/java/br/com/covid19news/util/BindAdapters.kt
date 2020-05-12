package br.com.covid19news.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.covid19news.domain.ResponseDomainModel
import br.com.covid19news.ui.adapter.AllCountriesAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, listData: List<ResponseDomainModel>?) {
    val adapter = recyclerView.adapter as AllCountriesAdapter
    adapter.submitList(listData)
}