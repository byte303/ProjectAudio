package com.example.projectaudio.ui.music

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectaudio.AdapterListMusic
import com.example.projectaudio.AudioModel
import com.example.projectaudio.R
import kotlinx.android.synthetic.main.fragment_music.view.*


class MusicFragment : Fragment() {

    private lateinit var musicViewModel: MusicViewModel
    private val PERMISSION_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        musicViewModel =
            ViewModelProviders.of(this).get(MusicViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_music, container, false)

        root.listMusic.layoutManager = LinearLayoutManager(context)
        return root
    }

    override fun onResume() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission())
                view!!.listMusic.adapter = AdapterListMusic(context!!, getAllAudioFromDevice(context!!))
            else
                requestPermission()
        }
        super.onResume()
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            (context as AppCompatActivity),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                (context as AppCompatActivity),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) showDialogPermission()
        else {
            ActivityCompat.requestPermissions(
                (context as AppCompatActivity),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    view!!.listMusic.adapter = AdapterListMusic(context!!,getAllAudioFromDevice(context!!))
                else
                    showDialogPermission()
            }
        }
    }

    private fun showDialogPermission(){
        val dialog = AlertDialog.Builder(context!!)
        dialog.setMessage("Требуется разрешение на доступ к хранилищу!")
        dialog.setTitle("Разрешение")
        dialog.setPositiveButton("Разрешить") { _, _ ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", (context as AppCompatActivity).packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        dialog.setNegativeButton("Запретить") { _, _ ->
            finishAffinity((context as AppCompatActivity))
        }
        dialog.show()
    }

    private fun getAllAudioFromDevice(context: Context): ArrayList<AudioModel> {
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