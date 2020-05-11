package br.com.covid19news.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatisticsRemote(
    @Json(name = "get") val get: String,
    //@Json(name = "parameters") val parameters: List<ParametersRemote>,
    //@Json(name = "errors") val errors: List<ErrorsRemote>,
    @Json(name = "results") val results: String,
    @Json(name = "response") val listResponseRemote: List<ResponseRemote>
) : Parcelable



