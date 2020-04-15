package br.com.covid19news.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.covid19news.R

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        val msg = when (item.itemId) {
            R.id.worldAll -> "World All"
            R.id.byCountry -> "By Country"
            R.id.allCountries -> "All Countries"
            else -> null
        }

        msg?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
