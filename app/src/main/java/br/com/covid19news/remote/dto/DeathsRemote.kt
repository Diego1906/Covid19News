package br.com.covid19news.remote.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeathsRemote(
    @Json(name = "new") val new: String?,
    @Json(name = "total") val total: String?
) : Parcelable