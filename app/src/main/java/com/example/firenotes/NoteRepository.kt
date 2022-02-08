package com.example.firenotes

class NoteRepository(private val notedao: Notedao) {
    val allNotes=notedao.getAllNotes()

    suspend fun insert(note: Note){
        notedao.insert(note)
    }
    suspend fun delete(note: Note){
        notedao.delete(note)
    }


}