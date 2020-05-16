package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covid19news.R
import br.com.covid19news.databinding.FragmentEntireWorldBinding
import br.com.covid19news.util.onNotifyWithToast
import br.com.covid19news.util.onToUpperCase
import br.com.covid19news.viewmodel.GenericViewModel
import kotlinx.android.synthetic.main.fragment_entire_world.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntireWorldFragment : Fragment() {

    private val viewModel: GenericViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEntireWorldBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.swipeRefreshEntireWorld.setOnRefreshListener { onShowData() }

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.onNotifyWithToast(Pair(requireContext(), viewModel))
        })

        viewModel.swipeIsRefreshing.observe(viewLifecycleOwner, Observer {
            swipeRefreshEntireWorld.isRefreshing = it
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
        if (savedInstanceState == null) {
            onShowData()
        }
    }

    private fun onShowData() {
        viewModel.onGetStatistics(
            Pair(getString(R.string.all).onToUpperCase(), true)
        )
    }
}
