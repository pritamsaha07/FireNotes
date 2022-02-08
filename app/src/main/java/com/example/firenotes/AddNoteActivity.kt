package com.example.firenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider

class AddNoteActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        viewModel=ViewModelProvider(this).get(NoteViewModel::class.java)
        val btn:Button=findViewById(R.id.addnote)
        btn.setOnClickListener{
            addingNote()
        }
    }
    private fun addingNote(){
        val edit:EditText=findViewById(R.id.input)
        val input=edit.text.toString()
        if(input.isNotEmpty()){
            viewModel.insertNote(Note(input))
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}