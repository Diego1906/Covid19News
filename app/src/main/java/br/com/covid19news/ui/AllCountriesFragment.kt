package br.com.covid19news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.covid19news.databinding.FragmentAllCountriesBinding
import br.com.covid19news.viewmodel.CovidViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCountriesFragment : Fragment() {

    private val viewModel: CovidViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAllCountriesBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}
