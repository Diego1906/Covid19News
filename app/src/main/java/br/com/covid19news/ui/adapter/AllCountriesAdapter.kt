package br.com.covid19news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.covid19news.databinding.ItemListCountriesBinding
import br.com.covid19news.domain.ResponseDomainModel

class AllCountriesAdapter(private val onclickListener: OnclickListener) :
    ListAdapter<ResponseDomainModel, ItemHolder>(DiffCallback) {

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

    fun bind(response: ResponseDomainModel) {
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

object DiffCallback : DiffUtil.ItemCallback<ResponseDomainModel>() {
    override fun areItemsTheSame(
        oldItem: ResponseDomainModel, newItem: ResponseDomainModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ResponseDomainModel, newItem: ResponseDomainModel
    ): Boolean {
        return oldItem.country == newItem.country && oldItem.cases.total == newItem.cases.total
    }
}

class OnclickListener(private val block: (ResponseDomainModel) -> Unit) {
    fun onClick(response: ResponseDomainModel) = block.invoke(response)
}