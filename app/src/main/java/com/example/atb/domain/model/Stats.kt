package com.example.atb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Stats(
    val totalStudent: Int,
    val totalSubject: Int
) : Parcelable
