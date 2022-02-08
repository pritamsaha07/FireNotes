package com.example.firenotes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Notedao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(note: Note)
    @Delete
    abstract fun delete(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    abstract fun getAllNotes(): LiveData<List<Note>>

}