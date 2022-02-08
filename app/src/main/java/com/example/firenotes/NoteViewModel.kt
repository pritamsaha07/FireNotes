package com.example.firenotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    val allNotes:LiveData<List<Note>>
    private val repository: NoteRepository

    init {
            val dao=NoteDatabase.getDatabase(application).getNodes()
             repository= NoteRepository(dao)
        allNotes=repository.allNotes
    }
    fun insertNote(note:Note)=viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun deleteNote(note:Note)=viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
}