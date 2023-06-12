package com.example.myandroid.data

class CounterRepository(private val counterDao: CounterDao) {
    fun getAll() = counterDao.getAll()

    suspend fun insert(counter: Counter) = counterDao.insert(counter)

    suspend fun update(counter: Counter) = counterDao.update(counter)

    suspend fun deleteById(CounterId: Int) = counterDao.deleteById(CounterId)

    suspend fun incrementCounterById(CounterId: Int) = counterDao.incrementCounterById(CounterId)

    suspend fun decrementCounterById(CounterId: Int) = counterDao.decrementCounterById(CounterId)
}