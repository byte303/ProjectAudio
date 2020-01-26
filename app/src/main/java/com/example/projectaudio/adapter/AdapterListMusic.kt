package com.example.projectaudio.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectaudio.helper.AudioModel
import com.example.projectaudio.R
import com.example.projectaudio.ui.music.MusicFragment
import kotlinx.android.synthetic.main.custom_list_music.view.*

class AdapterListMusic(
    private val context : Context,
    private val array : ArrayList<AudioModel>) :
    RecyclerView.Adapter<AdapterListMusic.ViewHolder>() {

    private var places: InterfaceSound

    init {
        places = context as InterfaceSound
    }

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
        holder.linear.setOnClickListener {
            //holder.img.visibility = View.VISIBLE
            places.onClickItem(position)
        }
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val txtName : TextView =  view.txtName
        val txtAuthor : TextView = view.txtAuthor
        val linear : RelativeLayout = view.linear
        val img : ImageView = view.imgMusic
    }

    internal interface InterfaceSound {
        fun onClickItem(num : Int)
    }
}
