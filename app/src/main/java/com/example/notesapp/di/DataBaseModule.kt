package com.example.notesapp.di

import android.content.Context
import androidx.room.Room
import com.example.notesapp.data.dataBase.NotesDAO
import com.example.notesapp.data.dataBase.NotesDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    private const val DB_NAME = "notes_db"

    @Provides
    @Singleton
    fun provideNotesDataBase(@ApplicationContext context: Context): NotesDB{
        return Room.databaseBuilder(
            context,
            NotesDB::class.java,
            DB_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesDAO(db: NotesDB):NotesDAO{
        return db.notesDAO()
    }
}