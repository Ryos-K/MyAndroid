package com.example.myandroid.data

import com.example.myandroid.database.dao.CounterDao
import com.example.myandroid.database.entity.asExternalModel
import com.example.myandroid.model.Counter
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CounterRepository @Inject constructor(
    private val counterDao: CounterDao
) {
    fun observeAll() = counterDao.getAll().map { it.map { entity -> entity.asExternalModel() } }

    suspend fun insert(counter: Counter) = counterDao.insert(counter.asEntity())

    suspend fun update(counter: Counter) = counterDao.update(counter.asEntity())

    suspend fun deleteById(CounterId: Int) = counterDao.deleteById(CounterId)
}