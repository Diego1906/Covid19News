package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.covid19news.databinding.FragmentEntireWorldBinding
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.EntireWorldViewModel

class EntireWorldFragment : Fragment() {

    private val viewModel: EntireWorldViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, EntireWorldViewModel.Factory(activity.application))
            .get(EntireWorldViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentEntireWorldBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.onShowToast(requireContext())
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.onSortData(it)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onShowData(TypeSearch.ALL.value)
    }
}
