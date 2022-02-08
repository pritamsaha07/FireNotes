package com.example.firenotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(private val listner: IAdapter):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val allNotes=ArrayList<Note>()





    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView:TextView=itemView.findViewById(R.id.text)
        val delbut:ImageView=itemView.findViewById(R.id.deletebutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val viewholder=NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false))
        viewholder.delbut.setOnClickListener{
            listner.onNoteClick(allNotes[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       val currentitem= allNotes[position]
        holder.textView.text=currentitem.text
    }

    override fun getItemCount(): Int {
     return allNotes.size
    }

    fun updateNote(newData: List<Note>){
        allNotes.clear()
        allNotes.addAll(newData)
        notifyDataSetChanged()
    }
}
interface IAdapter{
    fun onNoteClick(note: Note)
}