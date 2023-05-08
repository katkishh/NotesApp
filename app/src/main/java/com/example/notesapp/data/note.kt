package com.example.notesapp.data

data class Note(
    val id:Long,
    val text:String,
    val image: ByteArray?,
)