package com.example.myandroid.data

import com.example.myandroid.model.Counter
import kotlinx.coroutines.flow.map

class CounterRepository(private val counterDao: CounterDao) {
    fun observeAll() = counterDao.getAll().map { it.map { entity -> entity.asExternalModel() } }

    suspend fun insert(counter: Counter) = counterDao.insert(counter.asEntity())

    suspend fun update(counter: Counter) = counterDao.update(counter.asEntity())

    suspend fun deleteById(CounterId: Int) = counterDao.deleteById(CounterId)
}