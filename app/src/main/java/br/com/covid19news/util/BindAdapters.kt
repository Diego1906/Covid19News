package br.com.covid19news.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.covid19news.domain.ResponseModel
import br.com.covid19news.ui.adapter.AllCountriesAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, listData: List<ResponseModel>?) {
    val adapter = recyclerView.adapter as AllCountriesAdapter
    adapter.submitList(listData)
}