package com.example.firenotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes_table")
data class Note(@ColumnInfo(name="text")val text :String) {
   @PrimaryKey(autoGenerate = true)var id=0
}