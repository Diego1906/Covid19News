package br.com.covid19news.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataStatisticsModel(
    val get: String,
    //val parameters: List<ParametersModel>,
    //val errors: List<ErrorsModel>,
    val results: Int,
    val response: List<ResponseModel>?
) : Parcelable

@Parcelize
data class ResponseModel(
    val country: String?,
    val cases: CasesModel?,
    val deaths: DeathsModel?,
    val tests: TestsModel?,
    val day: String?,
    val time: String?
) : Parcelable

@Parcelize
data class CasesModel(
    val new: String?,
    val active: Int,
    val critical: Int,
    val recovered: Int,
    val total: Int
) : Parcelable

@Parcelize
data class DeathsModel(
    val new: String?,
    val total: Int
) : Parcelable

@Parcelize
data class TestsModel(
    val total: Int?
) : Parcelable

@Parcelize
data class ParametersModel(
    val country: String?
) : Parcelable

@Parcelize
data class ErrorsModel(
    val errors: String?
) : Parcelable



