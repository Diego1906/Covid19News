package br.com.covid19news.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val get: String,
    //val parameters: List<Parameters>,
    //val errors: List<Errors>,
    val results: Int,
    val response: List<Response>?
) : Parcelable

@Parcelize
data class Response(
    val country: String?,
    val cases: Cases?,
    val deaths: Deaths?,
    val tests: Tests?,
    val day: String?,
    val time: String?
) : Parcelable

@Parcelize
data class Cases(
    val new: String?,
    val active: Int,
    val critical: Int,
    val recovered: Int,
    val total: Int
) : Parcelable

@Parcelize
data class Deaths(
    val new: String?,
    val total: Int
) : Parcelable

@Parcelize
data class Tests(
    val total: Int
) : Parcelable

@Parcelize
data class Parameters(
    val country: String?
) : Parcelable

data class Errors(
    val errors: String?
)



