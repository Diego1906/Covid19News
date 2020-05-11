package br.com.covid19news.remote.dto

import br.com.covid19news.data.CasesEntity
import br.com.covid19news.data.DeathsEntity
import br.com.covid19news.data.ResponseEntity
import br.com.covid19news.data.TestsEntity
import br.com.covid19news.util.onCheckDataReported
import br.com.covid19news.util.onFormatDateTime
import br.com.covid19news.util.onGetDateCalendar

fun StatisticsRemote.asDatabaseModel(): Array<ResponseEntity> {
    return listResponseRemote.map {
        ResponseEntity(
            country = it.country.onCheckDataReported(),
            cases = CasesEntity(
                new = it.cases.new.onCheckDataReported(),
                active = it.cases.active.onCheckDataReported(),
                critical = it.cases.critical.onCheckDataReported(),
                recovered = it.cases.recovered.onCheckDataReported(),
                total = it.cases.total.onCheckDataReported()
            ),
            deaths = DeathsEntity(
                new = it.deaths.new.onCheckDataReported(),
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

