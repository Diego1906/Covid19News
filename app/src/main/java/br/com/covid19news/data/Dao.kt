package br.com.covid19news.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatisticsDao {

    @Query("${Crud.SELECT_ALL_COLUMNS} ${Crud.GROUP_BY} ${Crud.ORDER_BY}")
    fun getAll(): LiveData<List<ResponseEntity>>

    @Query("${Crud.SELECT_ALL_COLUMNS} ${Crud.WHERE} ${Crud.GROUP_BY} ${Crud.ORDER_BY}")
    fun getByPrimaryKey(country: String): ResponseEntity

    @Query("${Crud.SELECT_COUNT} ${Crud.WHERE}")
    fun getCountResult(country: String): Int

    @Query(Crud.SELECT_COUNT)
    fun getCountTotalResult(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg response: ResponseEntity)
}