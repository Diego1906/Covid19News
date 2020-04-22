package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covid19news.R
import br.com.covid19news.databinding.FragmentByCountryBinding
import br.com.covid19news.util.onIsNetworkConnected
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.CovidViewModel
import kotlinx.android.synthetic.main.fragment_by_country.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ByCountryFragment : Fragment() {

    private val viewModel: CovidViewModel by viewModel()
    // private lateinit var typeSearch: TypeSearch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentByCountryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.swipeRefreshByCountry.setOnRefreshListener { onShowData("brazil") }

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
            swipeRefreshByCountry.isRefreshing = it
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        if (::typeSearch.isInitialized.not()) {
//                typeSearch =
//        }

        onShowData("brazil")

    }

    private fun onShowData(typeSearch: String) {
        viewModel.onHideSwipeRefresh()
        if (this.onIsNetworkConnected().not()) {
            viewModel.onShowToast(getString(R.string.no_internet_connection))
            return
        }
        viewModel.onShowData(typeSearch)
    }
}
