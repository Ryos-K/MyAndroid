package com.example.myandroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi

@Database(entities = [WordDefinitionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDefinitionDao(): WordDefinitionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = "app_database"
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}