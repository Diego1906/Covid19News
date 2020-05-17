package br.com.covid19news.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covid19news.R
import br.com.covid19news.util.onNavigate
import br.com.covid19news.util.onShowNotify
import br.com.covid19news.viewmodel.StartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : Fragment() {

    private val viewModel: StartViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.onShowNotify(requireActivity(), viewModel)
        })

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.entireWorld -> this.onNavigate(
                StartFragmentDirections.actionStartFragmentToEntireWorldFragment()
            )
            R.id.byCountry -> this.onNavigate(
                StartFragmentDirections.actionStartFragmentToByCountryFragment()
            )
            R.id.allCountries -> this.onNavigate(
                StartFragmentDirections.actionStartFragmentToAllCountriesFragment()
            )
        }
        return true
    }
}
