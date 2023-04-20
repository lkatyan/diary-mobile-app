package com.example.diarymobileapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.diarymobileapp.models.Item

@Database (entities = [Item::class], version = 1)
abstract class Db : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): Db {
            return Room.databaseBuilder(
                context.applicationContext,
                Db::class.java,
                "text.db"
            ).build()
        }
    }
}