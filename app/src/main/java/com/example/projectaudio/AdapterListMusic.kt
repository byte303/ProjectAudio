package com.example.projectaudio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_music.view.*

class AdapterListMusic(
    private val context : Context,
    private val array : ArrayList<AudioModel>) :
    RecyclerView.Adapter<AdapterListMusic.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.custom_list_music,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtAuthor.text = array[position].getaArtist()
        holder.txtName.text = array[position].getaName()
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val txtName : TextView =  view.txtName
        val txtAuthor : TextView = view.txtAuthor
    }
}