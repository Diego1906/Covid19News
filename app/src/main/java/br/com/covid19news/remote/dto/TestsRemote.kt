package br.com.covid19news.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestsRemote(
    @Json(name = "total") val total: String?
) : Parcelable