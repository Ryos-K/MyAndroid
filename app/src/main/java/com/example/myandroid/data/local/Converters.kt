package com.example.myandroid.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

@ProvidedTypeConverter
class Converters(
    private val moshi: Moshi
) {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return moshi.adapter<List<String>>(List::class.java).toJson(list)
    }

    @TypeConverter
    fun toList(json: String): List<String> {
        return moshi.adapter<List<String>>(List::class.java).fromJson(json) ?: emptyList()
    }

}