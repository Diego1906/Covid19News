package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covid19news.R
import br.com.covid19news.databinding.FragmentEntireWorldBinding
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onIsNetworkConnected
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.CovidViewModel
import kotlinx.android.synthetic.main.fragment_entire_world.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntireWorldFragment : Fragment() {

    private val viewModel: CovidViewModel by viewModel()
    private lateinit var typeSearch: TypeSearch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentEntireWorldBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.swipeRefreshDetail.setOnRefreshListener { onShowData(typeSearch) }

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.onShowToast(requireContext())
                viewModel.onShowToast(null)
            }
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.onSortData(it)
            }
        })

        viewModel.swipeIsRefreshing.observe(viewLifecycleOwner, Observer {
            swipeRefreshDetail.isRefreshing = it
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::typeSearch.isInitialized.not()) {
            typeSearch = TypeSearch.ALL
            onShowData(typeSearch)
        }
    }

    private fun onShowData(typeSearch: TypeSearch) {
        viewModel.onHideSwipeRefresh()
        if (this.onIsNetworkConnected().not()) {
            viewModel.onShowToast(getString(R.string.no_internet_connection))
            return
        }
        viewModel.onShowData(typeSearch)
    }
}
