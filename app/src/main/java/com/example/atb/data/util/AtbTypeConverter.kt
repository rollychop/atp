package com.example.atb.data.util

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

object AtbTypeConverter {

    @TypeConverter
    fun toString(localDateTime: LocalDateTime): String = localDateTime.toString()

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String): LocalDateTime = LocalDateTime.parse(dateTimeString)

}