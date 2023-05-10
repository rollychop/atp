package com.example.atb.presentation.home_screen

import android.os.Parcelable
import com.example.atb.domain.model.Stats
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeScreenState(
    val loading: Boolean = false,
    val error: String = "",
    val stats: Stats,
) : Parcelable
