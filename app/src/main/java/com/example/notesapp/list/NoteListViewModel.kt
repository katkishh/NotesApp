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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteUseCase: DeleteUseCase
) : ViewModel() {
    private val _notesListLiveData = MutableLiveData<List<Note>>()
    val notesListLiveData: LiveData<List<Note>> = _notesListLiveData

    fun onAddClicked(text: String, image: ByteArray?){
        viewModelScope.launch {
            addNoteUseCase.execute(text, image)
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