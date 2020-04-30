package br.com.covid19news.util

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.viewmodel.IViewModel
import java.text.SimpleDateFormat
import java.util.*

fun String.onShowToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.onFormatDateTime(): String {
    val context: Context = App.getContext()

    val dateParser = SimpleDateFormat(
        context.getString(R.string.pattern_date_api),
        Locale.ENGLISH
    ).apply {
        timeZone = TimeZone.getTimeZone(context.getString(R.string.time_zone_gmt))
    }

    val dateFormatter = SimpleDateFormat(
        context.getString(R.string.pattern_date_pt_br),
        Locale.getDefault()
    )

    var newDateFormated: String = Date().toString()

    val dateApi = dateParser.parse(this)
    dateApi?.let {
        newDateFormated = dateFormatter.format(it)
    }

    return newDateFormated
}

fun String.removePrefix(): String {
    return this.removePrefix(App.getContext().getString(R.string.str_plus))
}

fun Fragment.onIsNetworkConnected(): Boolean {
    val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.isConnectedOrConnecting == true
}

fun Fragment.onCheckInternetAndShowData(
    params: Triple<IViewModel, String?, TypeSearch>,
    isHideSwipe: Boolean = false
) {
    if (isHideSwipe)
        params.first.onHideSwipeRefresh()

    if (this.onIsNetworkConnected().not()) {
        params.first.onShowToast(getString(R.string.no_internet_connection))
        return
    }
    params.first.onShowData(params.second, params.third)
}

fun Fragment.onNavigate(directions: NavDirections) {
    this.findNavController().navigate(directions)
}
