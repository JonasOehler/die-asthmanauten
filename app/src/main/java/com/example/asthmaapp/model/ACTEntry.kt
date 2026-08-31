package com.example.asthmaapp.model;
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable;



@Parcelize
data class ACTEntry(
        val id: String,
        val date: String,
        val score: Int,
        val answers: List<String>,
        val resultType: ACTResultType
): Parcelable