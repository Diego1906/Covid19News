package br.com.covid19news.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Response(
    @Json(name = "country") val country: String?,
    @Json(name = "cases") val cases: Cases?,
    @Json(name = "deaths") val deaths: Deaths?,
    @Json(name = "tests") val tests: Tests?,
    @Json(name = "day") val day: String?,
    @Json(name = "time") val time: String?
) : Parcelable