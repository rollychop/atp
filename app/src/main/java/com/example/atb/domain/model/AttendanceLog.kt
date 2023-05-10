package com.example.atb.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler


@Entity(
    tableName = "attendance_log",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["barcode"],
            childColumns = ["barcode"],
            onDelete = CASCADE,
        ),
    ],
    indices = [
        Index("barcode")
    ]
)
@Parcelize
data class AttendanceLog(

    val barcode: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @TypeParceler<LocalDateTime, LocalDateTimeParceler>()
    val date: LocalDateTime,
    val subject: String = ""

) : Parcelable

object LocalDateTimeParceler : Parceler<LocalDateTime> {
    override fun create(parcel: Parcel): LocalDateTime {
        val date = parcel.readString()
        return date?.toLocalDateTime() ?: LocalDateTime(0, 0, 0, 0, 0)
    }

    override fun LocalDateTime.write(parcel: Parcel, flags: Int) {
        parcel.writeString(this.toString())
    }
}
