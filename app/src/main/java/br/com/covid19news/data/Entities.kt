package br.com.covid19news.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.covid19news.domain.CasesModel
import br.com.covid19news.domain.DeathsModel
import br.com.covid19news.domain.ResponseModel
import br.com.covid19news.domain.TestsModel

@Entity(tableName = "currentstatistics", indices = [Index(value = ["id"], unique = true)])
data class ResponseEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var country: String? = null,
    @Embedded(prefix = "cases_") var cases: CasesEntity? = null,
    @Embedded(prefix = "deaths_") var deaths: DeathsEntity? = null,
    @Embedded(prefix = "tests_") var tests: TestsEntity? = null,
    var day: String? = null,
    var time: String? = null
)

data class CasesEntity(
    var new: String? = null,
    var active: String? = null,
    var critical: String? = null,
    var recovered: String? = null,
    var total: String? = null
)

data class DeathsEntity(
    var new: String? = null,
    var total: String? = null
)

data class TestsEntity(
    var total: String? = null
)

// Convert from database objects to model objects
fun List<ResponseEntity>.asDomainModel(): List<ResponseModel> {
    return map {
        ResponseModel(
            country = it.country
            ,
            cases = CasesModel(
                new = it.cases?.new,
                active = it.cases?.active,
                critical = it.cases?.critical,
                recovered = it.cases?.recovered,
                total = it.cases?.total
            ),
            deaths = DeathsModel(
                new = it.deaths?.new,
                total = it.deaths?.total
            ),
            tests = TestsModel(
                total = it.tests?.total
            ),
            day = it.day,
            time = it.time
        )
    }
}

