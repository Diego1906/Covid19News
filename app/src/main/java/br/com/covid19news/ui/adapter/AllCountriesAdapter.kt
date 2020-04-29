package br.com.covid19news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.covid19news.databinding.ItemListCountriesBinding
import br.com.covid19news.domain.ResponseModel

class AllCountriesAdapter(private val onclickListener: OnclickListener) :
    ListAdapter<ResponseModel, ItemHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val response = getItem(position)
        holder.itemView.setOnClickListener {
            onclickListener.onClick(response)
        }
        holder.bind(response)
    }
}

class ItemHolder private constructor(private val binding: ItemListCountriesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(response: ResponseModel) {
        binding.response = response
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ItemHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = ItemListCountriesBinding.inflate(inflater)
            return ItemHolder(view)
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<ResponseModel>() {
    override fun areItemsTheSame(oldItem: ResponseModel, newItem: ResponseModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ResponseModel, newItem: ResponseModel): Boolean {
        return oldItem.country == newItem.country && oldItem.cases?.total == newItem.cases?.total
    }
}

class OnclickListener(private val block: (ResponseModel) -> Unit) {
    fun onClick(response: ResponseModel) = block.invoke(response)
}