package com.example.diarymobileapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.diarymobileapp.models.Item
import kotlinx.coroutines.flow.Flow
import java.sql.Timestamp

@Dao
interface Dao {

    @Insert
    fun insertItem(item: Item)

    @Query("SELECT * FROM items WHERE date_start > :dateStart AND date_start < :dateFinish")
    fun getItems(dateStart: Long, dateFinish: Long): Flow<List<Item>>
}