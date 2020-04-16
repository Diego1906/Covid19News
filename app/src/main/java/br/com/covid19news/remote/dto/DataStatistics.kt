package br.com.covid19news.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataStatistics(
    @Json(name = "get") val get: String,
    //@Json(name = "parameters") val parameters: List<Parameters>,
    //@Json(name = "errors") val errors: List<Errors>,
    @Json(name = "results") val results: Int,
    @Json(name = "response") val response: List<Response>?
) : Parcelable



