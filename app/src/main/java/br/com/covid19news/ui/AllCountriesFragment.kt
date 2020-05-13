package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covid19news.R
import br.com.covid19news.databinding.FragmentAllCountriesBinding
import br.com.covid19news.ui.adapter.AllCountriesAdapter
import br.com.covid19news.ui.adapter.OnclickListener
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onNavigate
import br.com.covid19news.util.onNotifyWithToast
import br.com.covid19news.viewmodel.GenericViewModel
import kotlinx.android.synthetic.main.fragment_all_countries.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCountriesFragment : Fragment() {

    private val viewModel: GenericViewModel by viewModel()
    private val recyclerAdapter by lazy {
        AllCountriesAdapter(OnclickListener {
            this.onNavigate(
                AllCountriesFragmentDirections.actionAllCountriesFragmentToDetailFragment(it)
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAllCountriesBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerAllCountries.adapter = recyclerAdapter
        binding.swipeRefreshAllCountries.setOnRefreshListener { onShowData() }

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.onNotifyWithToast(Pair(requireContext(), viewModel))
        })

        viewModel.swipeIsRefreshing.observe(viewLifecycleOwner, Observer {
            swipeRefreshAllCountries.isRefreshing = it
        })

        viewModel.isNotNetworkConnected.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    viewModel.onShowToast(getString(R.string.no_internet_connection))
                    viewModel.onIsNotNetworkConnectedComplete()
                }
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onShowData()
    }

    private fun onShowData() {
        viewModel.onShowData(Triple(true, null, TypeSearch.Statistcs))
    }
}
