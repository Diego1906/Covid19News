package br.com.covid19news.mapper

import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.domain.*
import br.com.covid19news.remote.dto.*
import br.com.covid19news.util.*

fun StatisticsRemote.mapToModel() = StatisticsModel(
    get = this.get.onCheckDataReported(),
    //parameters = this.parameters.map { it.mapToModel() },
    //errors = this.errors.map { it.mapToModel() },
    results = this.results.onCheckDataReported(),
    listResponse = this.listResponseRemote?.map { it.mapToModel() }
)

fun ResponseRemote.mapToModel() = ResponseModel(
    country = when (this.country) {
        TypeSearch.All.name -> App.getContext().getString(R.string.all)
        else -> this.country
    } ?: this.country.onCheckDataReported(),
    cases = this.cases.mapToModel(),
    deaths = this.deaths.mapToModel(),
    tests = this.tests.mapToModel(),
    day = this.day.onCheckDataReported(),
    time = this.time?.onFormatDateTime() ?: onGetDateCalendar()
)

fun CasesRemote.mapToModel() = CasesModel(
    new = this.new.onRemovePrefix(),
    active = this.active.onCheckDataReported(),
    critical = this.critical.onCheckDataReported(),
    recovered = this.recovered.onCheckDataReported(),
    total = this.total.onCheckDataReported()
)

fun DeathsRemote.mapToModel() = DeathsModel(
    new = this.new.onRemovePrefix(),
    total = this.total.onCheckDataReported()
)

fun TestsRemote.mapToModel() = TestsModel(
    total = this.total.onCheckDataReported()
)

fun ParametersRemote.mapToModel() = ParametersModel(
    country = this.country.onCheckDataReported()
)

fun ErrorsRemote.mapToModel() = ErrorsModel(
    errors = this.errors.onCheckDataReported()
)

