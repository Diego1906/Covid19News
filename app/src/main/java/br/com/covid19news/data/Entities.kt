package br.com.covid19news.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.covid19news.domain.CasesModel
import br.com.covid19news.domain.DeathsModel
import br.com.covid19news.domain.ResponseModel
import br.com.covid19news.domain.TestsModel

@Entity(
    tableName = "currentstatistics", indices = [Index(value = ["country"], unique = true)]
)
data class ResponseEntity(
    @PrimaryKey var country: String,
    @Embedded(prefix = "cases_") var cases: CasesEntity,
    @Embedded(prefix = "deaths_") var deaths: DeathsEntity,
    @Embedded(prefix = "tests_") var tests: TestsEntity,
    var day: String,
    var time: String
)

data class CasesEntity(
    var new: String = "",
    var active: String = "",
    var critical: String = "",
    var recovered: String = "",
    var total: String = ""
)

data class DeathsEntity(
    var new: String = "",
    var total: String = ""
)

data class TestsEntity(
    var total: String = ""
)

// Convert from database objects to model objects
fun List<ResponseEntity>.asDomainModel(): List<ResponseModel> {
    return map {
        ResponseModel(
            country = it.country,
            cases = CasesModel(
                new = it.cases.new,
                active = it.cases.active,
                critical = it.cases.critical,
                recovered = it.cases.recovered,
                total = it.cases.total
            ),
            deaths = DeathsModel(
                new = it.deaths.new,
                total = it.deaths.total
            ),
            tests = TestsModel(
                total = it.tests.total
            ),
            day = it.day,
            time = it.time
        )
    }
}

