package com.example.firenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), IAdapter {
    private lateinit var viewmodel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Firebase.auth.signOut()
        val recylerview:RecyclerView=findViewById(R.id.Recycler)
        recylerview.layoutManager=LinearLayoutManager(this)
        val adapter= NewsAdapter(this)
        recylerview.adapter=adapter
        viewmodel=ViewModelProvider(this).get(NoteViewModel::class.java)
        viewmodel.allNotes.observe(this, Observer{
            adapter.updateNote(it)
        })

    }

    fun addNote(view: View) {
        val intent=Intent(this,AddNoteActivity::class.java)
        startActivity(intent)
    }

    override fun onNoteClick(note: Note) {
       viewmodel.deleteNote(note)
    }
}