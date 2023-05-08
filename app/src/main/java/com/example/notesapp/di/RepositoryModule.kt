package com.example.notesapp.di

import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.NotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindNotesRepository(impl: NotesRepositoryImpl): NotesRepository
}