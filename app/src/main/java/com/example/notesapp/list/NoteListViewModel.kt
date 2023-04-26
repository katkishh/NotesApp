package com.example.notesapp.list

import com.example.notesapp.domain.GetNotesUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.dataBase.model.NoteEntity
import com.example.notesapp.domain.AddNoteUseCase
import com.example.notesapp.domain.DeleteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListViewModel(
    private val getNotesUseCase: GetNotesUseCase = GetNotesUseCase(),
    private val addNoteUseCase: AddNoteUseCase = AddNoteUseCase(),
    private val deleteUseCase: DeleteUseCase = DeleteUseCase()
) : ViewModel() {
    private val _notesListLiveData = MutableLiveData<List<Note>>()
    val notesListLiveData: LiveData<List<Note>> = _notesListLiveData

    fun onAddClicked(text: String){
        viewModelScope.launch {
            addNoteUseCase.execute(text)
        }
    }

    fun onDeleteClicked(note: NoteEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){deleteUseCase.execute(note)}
        }
    }

    fun getNotes(){
        viewModelScope.launch {
            getNotesUseCase.execute().collect{ list ->
                _notesListLiveData.value = list.map { note ->
                    note.copy(
                        text = note.text
                    )
                }
            }
        }
    }

    //fun MutableLiveData<*>.toLiveData() = this as LiveData<*>
}