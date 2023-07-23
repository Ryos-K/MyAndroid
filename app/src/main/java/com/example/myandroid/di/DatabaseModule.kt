package com.example.myandroid.di

import android.content.Context
import androidx.room.Room
import com.example.myandroid.database.AppDatabase
import com.example.myandroid.database.utils.Converters
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = "app_database"
    )
        .addTypeConverter(Converters(Moshi.Builder().build()))
        .fallbackToDestructiveMigration().build()
}