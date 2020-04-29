package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covid19news.R
import br.com.covid19news.databinding.FragmentByCountryBinding
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onIsNetworkConnected
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.CovidViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ByCountryFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: CovidViewModel by viewModel()
    private var filter: String = ""
    private lateinit var binding: FragmentByCountryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentByCountryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.buttonSearch.setOnClickListener { onShowData(filter) }
        binding.spinnerPais.onItemSelectedListener = this

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.onShowToast(requireContext())
                viewModel.onShowToast(null)
            }
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.onIsVisibleCardView(true)
                viewModel.onSetCountryData(it)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitializeSpinner()
    }

    private fun onInitializeSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.listCountries,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerPais.adapter = adapter
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        filter = resources.getStringArray(R.array.filterCountries)[position]
    }

    private fun onShowData(filter: String) {
        if (this.onIsNetworkConnected().not()) {
            viewModel.onShowToast(getString(R.string.no_internet_connection))
            return
        }
        viewModel.onShowData(filter, TypeSearch.Country)
    }
}
