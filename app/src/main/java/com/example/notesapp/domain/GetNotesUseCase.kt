package com.example.notesapp.domain
import com.example.notesapp.data.Note
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.NotesRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(
    private val notesRepository: NotesRepository = NotesRepositoryImpl()
) {
    fun execute(): Flow<List<Note>>{
        return notesRepository.getNotes()
    }
}