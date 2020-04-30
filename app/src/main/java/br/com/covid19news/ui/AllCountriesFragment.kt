package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covid19news.databinding.FragmentAllCountriesBinding
import br.com.covid19news.ui.adapter.AllCountriesAdapter
import br.com.covid19news.ui.adapter.OnclickListener
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onCheckInternetAndShowData
import br.com.covid19news.util.onNavigate
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.CovidViewModel
import kotlinx.android.synthetic.main.fragment_all_countries.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCountriesFragment : Fragment() {

    private val viewModel: CovidViewModel by viewModel()
    private lateinit var typeSearch: TypeSearch
    private val recyclerAdapter by lazy {
        AllCountriesAdapter(OnclickListener {
            this.onNavigate(
                AllCountriesFragmentDirections.actionAllCountriesFragmentToDetailFragment(it)
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAllCountriesBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerAllCountries.adapter = recyclerAdapter
        binding.swipeRefreshAllCountries.setOnRefreshListener { onShowData() }

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.onShowToast(requireContext())
                viewModel.onShowToast(null)
            }
        })

        viewModel.swipeIsRefreshing.observe(viewLifecycleOwner, Observer {
            it?.let {
                swipeRefreshAllCountries.isRefreshing = it
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::typeSearch.isInitialized.not()) {
            typeSearch = TypeSearch.Statistcs
            onShowData()
        }
    }

    private fun onShowData() {
        this.onCheckInternetAndShowData(Triple(viewModel, null, typeSearch), true)
    }
}
