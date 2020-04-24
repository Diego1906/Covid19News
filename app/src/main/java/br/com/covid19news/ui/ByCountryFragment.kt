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
import br.com.covid19news.util.onIsNetworkConnected
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.CovidViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ByCountryFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: CovidViewModel by viewModel()
    private var country: String = ""
    private lateinit var binding: FragmentByCountryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentByCountryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.buttonSearch.setOnClickListener { onShowData(country) }
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
                viewModel.onSortData(it)
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
        val indexItem = parent?.getItemIdAtPosition(position)?.toInt()
        val filters = resources.getStringArray(R.array.filterCountries)

        for (i in 0..filters.size)
            if (indexItem != null) {
                country = filters[indexItem]
                break
            }

/*        country = when (parent?.getItemIdAtPosition(position)?.toInt()) {
            0 -> Countries.GERMANY.value
            1 -> Countries.AUSTRALIA.value
            2 -> Countries.BELGIUM.value
            3 -> Countries.BRAZIL.value
            4 -> Countries.CANADA.value
            5 -> Countries.CHINA.value
            6 -> Countries.SPAIN.value
            7 -> Countries.UNITED_STATES.value
            8 -> Countries.FRANCE.value
            9 -> Countries.INDIA.value
            10 -> Countries.IRELAND.value
            11 -> Countries.ISRAEL.value
            12 -> Countries.ITALY.value
            13 -> Countries.JAPAN.value
            14 -> Countries.PORTUGAL.value
            else -> Countries.UNITED_KINGDOM.value
        }*/
    }

    private fun onShowData(country: String) {
        if (this.onIsNetworkConnected().not()) {
            viewModel.onShowToast(getString(R.string.no_internet_connection))
            return
        }
        viewModel.onShowData(country)
    }
}
