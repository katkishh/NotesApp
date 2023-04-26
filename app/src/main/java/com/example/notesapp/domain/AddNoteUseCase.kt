package com.example.notesapp.domain

import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.NotesRepositoryImpl

class AddNoteUseCase(
    private val notesRepository: NotesRepository = NotesRepositoryImpl()
) {
    suspend fun execute(text:String){
        notesRepository.addNote(text)
    }
}