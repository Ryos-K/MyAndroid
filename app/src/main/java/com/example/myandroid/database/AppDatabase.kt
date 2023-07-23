package com.example.myandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myandroid.database.dao.CounterDao
import com.example.myandroid.database.dao.WordDefinitionDao
import com.example.myandroid.database.entity.CounterEntity
import com.example.myandroid.database.entity.WordDefinitionEntity
import com.example.myandroid.database.utils.Converters
import com.squareup.moshi.Moshi


@Database(
    entities = [WordDefinitionEntity::class, CounterEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDefinitionDao(): WordDefinitionDao
    abstract fun counterDao(): CounterDao
}