package br.com.covid19news.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRemote(
    @Json(name = "country") val country: String?,
    @Json(name = "cases") val cases: CasesRemote,
    @Json(name = "deaths") val deaths: DeathsRemote,
    @Json(name = "tests") val tests: TestsRemote,
    @Json(name = "day") val day: String?,
    @Json(name = "time") val time: String?
) : Parcelable