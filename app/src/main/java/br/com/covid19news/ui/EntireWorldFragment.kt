package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covid19news.databinding.FragmentEntireWorldBinding
import br.com.covid19news.util.TypeSearch
import br.com.covid19news.util.onCheckInternetAndShowData
import br.com.covid19news.util.onShowToast
import br.com.covid19news.viewmodel.CovidViewModel
import kotlinx.android.synthetic.main.fragment_entire_world.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntireWorldFragment : Fragment() {

    private val viewModel: CovidViewModel by viewModel()
    private lateinit var filter: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEntireWorldBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.swipeRefreshEntireWorld.setOnRefreshListener { onShowData() }

        viewModel.toast.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.onShowToast(requireContext())
                viewModel.onShowToast(null)
            }
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.onSetStatusData(it)
            }
        })

        viewModel.swipeIsRefreshing.observe(viewLifecycleOwner, Observer {
            swipeRefreshEntireWorld.isRefreshing = it
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::filter.isInitialized.not()) {
            filter = TypeSearch.All.name
            onShowData()
        }
    }

    private fun onShowData() {
        this.onCheckInternetAndShowData(Triple(viewModel, filter, TypeSearch.All), true)
    }
}
