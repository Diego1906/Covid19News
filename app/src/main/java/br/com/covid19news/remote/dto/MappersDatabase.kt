package br.com.covid19news.remote.dto

import br.com.covid19news.data.CasesEntity
import br.com.covid19news.data.DeathsEntity
import br.com.covid19news.data.ResponseEntity
import br.com.covid19news.data.TestsEntity

fun DataStatistics.asDatabaseModel(): Array<ResponseEntity> {
    return listResponse?.map {
        ResponseEntity(
            country = it.country,
            cases = CasesEntity(
                new = it.cases?.new,
                active = it.cases?.active,
                critical = it.cases?.critical,
                recovered = it.cases?.recovered,
                total = it.cases?.total
            ),
            deaths = DeathsEntity(
                new = it.deaths?.new,
                total = it.deaths?.total
            ),
            tests = TestsEntity(
                total = it.tests?.total
            ),
            day = it.day,
            time = it.time
        )
    }!!.toTypedArray()
}

