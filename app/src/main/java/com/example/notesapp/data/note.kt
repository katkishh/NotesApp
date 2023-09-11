package com.example.notesapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id:Long,
    val text:String,
    val image: ByteArray?,
): Parcelable