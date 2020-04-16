package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.covid19news.R
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.AllCountriesViewModel

class EntireWorldFragment : Fragment() {

    private val viewModel: AllCountriesViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, AllCountriesViewModel.Factory(activity.application))
            .get(AllCountriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entire_world, container, false)

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.onShowToast(requireContext())
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.response?.get(0)?.country.toString().onShowToast(requireContext())
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onShowData(TypeSearch.ALL.value)
    }
}
