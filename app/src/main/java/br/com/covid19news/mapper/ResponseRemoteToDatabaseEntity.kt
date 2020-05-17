package br.com.covid19news.mapper

import br.com.covid19news.R
import br.com.covid19news.application.App
import br.com.covid19news.data.CasesEntity
import br.com.covid19news.data.DeathsEntity
import br.com.covid19news.data.ResponseEntity
import br.com.covid19news.data.TestsEntity
import br.com.covid19news.remote.dto.StatisticsRemote
import br.com.covid19news.util.*

fun StatisticsRemote.asDatabaseModel(): Array<ResponseEntity> {
    return responsesRemote.map {
        ResponseEntity(
            country = when (it.country) {
                App.getContext().getString(R.string.all) -> {
                    App.getContext().getString(R.string.all).onToUpperCase()
                }
                else -> it.country?.trim()?.onToUpperCase()
            } ?: it.country.onCheckDataReported(),
            cases = CasesEntity(
                newer = it.cases.new.onRemovePrefix(),
                active = it.cases.active.onCheckDataReported(),
                critical = it.cases.critical.onCheckDataReported(),
                recovered = it.cases.recovered.onCheckDataReported(),
                total = it.cases.total.onCheckDataReported()
            ),
            deaths = DeathsEntity(
                newer = it.deaths.new.onRemovePrefix(),
                total = it.deaths.total.onCheckDataReported()
            ),
            tests = TestsEntity(
                total = it.tests.total.onCheckDataReported()
            ),
            day = it.day.onCheckDataReported(),
            time = it.time?.onFormatDateTime() ?: onGetDateCalendar()
        )
    }.toTypedArray()
}