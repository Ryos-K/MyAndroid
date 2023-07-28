package com.example.myandroid.di

import com.example.myandroid.database.AppDatabase
import com.example.myandroid.database.dao.CounterDao
import com.example.myandroid.database.dao.WordDefinitionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideCounterDao(
        database: AppDatabase
    ): CounterDao = database.counterDao()

    @Provides
    fun provideWordDefinitionDao(
        database: AppDatabase
    ): WordDefinitionDao = database.wordDefinitionDao()
}