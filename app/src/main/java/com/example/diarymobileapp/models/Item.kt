package com.example.diarymobileapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity (tableName = "items")
data class Item (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "date_start")
    var date_start: Long?,
    @ColumnInfo(name = "date_finish")
    var date_finish: Long?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "description")
    var description: String?
        )
