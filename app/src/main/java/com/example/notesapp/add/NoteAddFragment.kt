package com.example.notesapp.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNoteBinding
import com.example.notesapp.list.NoteListViewModel

class NoteAddFragment: Fragment(R.layout.fragment_add_note) {
    private val binding by viewBinding(FragmentAddNoteBinding::bind)
    private val viewModelList by viewModels<NoteListViewModel>()

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

    private fun submitNote(text:String){
        viewModelList.onAddClicked(text)

        findNavController().safeNavigate(
            NoteAddFragmentDirections.actionNoteAddFragmentToNoteListFragment()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            toolbar.setNavigationOnClickListener {
                val text = editTextNoteText.text.toString()
                submitNote(text)
            }

            fabSubmitNote.setOnClickListener {
                val text = editTextNoteText.text.toString()
                submitNote(text)
            }
        }
    }
}