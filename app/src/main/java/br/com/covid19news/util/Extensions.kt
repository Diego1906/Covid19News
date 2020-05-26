package br.com.covid19news.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.viewmodel.IBaseViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun String.onShowNotify(activity: Activity, viewModel: IBaseViewModel) {
    Snackbar.make(activity.findViewById(android.R.id.content), this, Snackbar.LENGTH_LONG).show()
    viewModel.onShowToast(null)
}

fun String.onFormatDateTime(): String {
    val dateParserApi = SimpleDateFormat(
        App.getContext().getString(R.string.pattern_date_api),
        Locale.ENGLISH
    ).apply {
        timeZone = TimeZone.getTimeZone(App.getContext().getString(R.string.time_zone_gmt))
    }

    val dateFormatterLocal = SimpleDateFormat(
        App.getContext().getString(R.string.pattern_date_pt_br),
        Locale.getDefault()
    )

    var newFormatedDate: String = Date().toString()

    val dateApi = dateParserApi.parse(this)
    dateApi?.let {
        newFormatedDate = dateFormatterLocal.format(it)
    }

    return newFormatedDate
}

fun String?.onRemovePrefix(): String {
    return this?.removePrefix("+") ?: this.onCheckDataReported()
}

fun String?.onCheckDataReported(): String {
    return this ?: App.getContext().getString(R.string.data_not_reported)
}

fun String.onToUpperCase() = this.toUpperCase(Locale.getDefault())

fun onGetDateCalendar(): String {
    return Calendar.getInstance().time.toString().onFormatDateTime()
}

fun Context.onIsNetworkConnected(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting == true
}

fun Fragment.onNavigate(directions: NavDirections) {
    findNavController().navigate(directions)
}