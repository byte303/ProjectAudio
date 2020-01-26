package com.example.projectaudio.helper

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

class Helper{
    fun getAllAudioFromDevice(context: Context): ArrayList<AudioModel> {
        val tempAudioList: ArrayList<AudioModel> = ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST
        )
        val c : Cursor = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )!!
        while (c.moveToNext()) {
            val audioModel = AudioModel()
            val path: String = c.getString(0)
            val album: String = c.getString(1)
            val artist: String = c.getString(2)
            val name = path.substring(path.lastIndexOf("/") + 1)
            audioModel.setaName(name)
            audioModel.setaAlbum(album)
            audioModel.setaArtist(artist)
            audioModel.setaPath(path)
            Log.e("Name :$name", " Album :$album")
            Log.e("Path :$path", " Artist :$artist")
            tempAudioList.add(audioModel)
        }
        c.close()
        return tempAudioList
    }
}