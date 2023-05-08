package com.example.notesapp.domain
import com.example.notesapp.data.Note
import com.example.notesapp.data.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    fun execute(): Flow<List<Note>>{
        return notesRepository.getNotes()
    }
}