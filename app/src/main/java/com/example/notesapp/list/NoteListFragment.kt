package com.example.notesapp.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notesapp.R
import com.example.notesapp.data.mappers.NotesMapper
import com.example.notesapp.databinding.FragmentNotesListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteListFragment: Fragment(R.layout.fragment_notes_list) {
    private val binding by viewBinding(FragmentNotesListBinding::bind)
    private val viewModel by viewModels<NoteListViewModel>()

    @Inject lateinit var listAdapter: ListFragmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotes()

        with(binding){

            recyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

                adapter = listAdapter.apply {
                    onNoteClick = {note ->
                        findNavController().navigate(
                            NoteListFragmentDirections.actionNoteListFragmentToNoteAddFragment(note)
                        )
                    }
                    onNoteLongClick = {note ->
                        viewModel.onDeleteClicked(NotesMapper().fromUiModelToEntity(note))
                    }
                }
            }

            floatingActionButton.setOnClickListener {
                    findNavController().safeNavigate(
                    NoteListFragmentDirections.actionNoteListFragmentToNoteAddFragment(null)
                )
            }
        }

        viewModel.notesListLiveData.observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

}