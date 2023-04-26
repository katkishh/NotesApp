package com.example.notesapp

import android.app.Application

class NotesApplication: Application(){
    companion object{
        private var instance: NotesApplication? = null

        fun getApplicationContext() = instance?.applicationContext
    }

    init {
        instance = this
    }
}