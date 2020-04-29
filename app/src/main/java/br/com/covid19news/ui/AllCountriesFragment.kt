package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.covid19news.databinding.FragmentAllCountriesBinding
import br.com.covid19news.ui.adapter.AllCountriesAdapter
import br.com.covid19news.ui.adapter.OnclickListener
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.CovidViewModel
import kotlinx.android.synthetic.main.fragment_all_countries.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCountriesFragment : Fragment() {

    private val viewModel: CovidViewModel by viewModel()

    private val recyclerAdapter by lazy {
        AllCountriesAdapter(OnclickListener {
            viewModel.onShowToast(null)
            this.findNavController().navigate(
                AllCountriesFragmentDirections.actionAllCountriesFragmentToDetailFragment(it)
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAllCountriesBinding.inflate(
            inflater, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerAllCountries.adapter = recyclerAdapter
        binding.swipeRefreshAllCountries.setOnRefreshListener { }

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.onShowToast(requireContext())
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
    }
}
