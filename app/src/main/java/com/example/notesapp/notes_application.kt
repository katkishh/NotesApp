package com.example.notesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotesApplication: Application(){
    companion object{
        private var instance: NotesApplication? = null

        fun getApplicationContext() = instance?.applicationContext
    }

    init {
        instance = this
    }
}