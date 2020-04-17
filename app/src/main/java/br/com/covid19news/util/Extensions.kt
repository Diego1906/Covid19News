package br.com.covid19news.util

import android.content.Context
import android.widget.Toast
import br.com.covid19news.R
import br.com.covid19news.application.CovidApplication
import java.text.SimpleDateFormat
import java.util.*

fun String.onShowToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.onFormatDateTime(): String {
    val context: Context = CovidApplication.getContext()

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
    return this.removePrefix(CovidApplication.getContext().getString(R.string.str_plus))
}