package com.example.projectaudio

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projectaudio.adapter.AdapterListMusic
import com.example.projectaudio.helper.AudioModel
import com.example.projectaudio.helper.Helper
import kotlinx.android.synthetic.main.bottom_sheet_sound.*

class MainActivity : AppCompatActivity(), AdapterListMusic.InterfaceSound {

    private var arraySound : ArrayList<AudioModel> = ArrayList()

    override fun onClickItem(num: Int) {
        onPlaySound(num)
    }

    private var checkPlay = false
    private var mediaPlayer: MediaPlayer? = null
    private var numberTrack: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        arraySound = Helper().getAllAudioFromDevice(this)

        mediaPlayer = MediaPlayer.create(this, Uri.parse(arraySound[0].getaPath()))
        mediaPlayer?.setOnPreparedListener {

        }
        onSelect(0)
    }

    private fun onPlaySound(num: Int, play: Boolean = false) {

        if (checkPlay) onStopSound()

        if (!play) mediaPlayer = MediaPlayer.create(this, Uri.parse(arraySound[num].getaPath()))

        mediaPlayer?.start()

        imgPlay.setImageResource(R.drawable.ic_pause)
        checkPlay = true
        numberTrack = num
        onSelect(num)

        mediaPlayer?.setOnCompletionListener {
            if (numberTrack == arraySound.size - 1)
                onPlaySound(0)
            else {
                numberTrack += 1
                onPlaySound(numberTrack)
            }
        }
    }

    private fun onStopSound() {
        mediaPlayer?.pause()
        imgPlay.setImageResource(R.drawable.ic_play)
        checkPlay = false
    }

    private fun onSelect(num: Int) {
        txtNameMusic.text = arraySound[num].getaName()
        txtAuthorMusic.text = arraySound[num].getaArtist()

        imgPlay.setOnClickListener {
            if (!checkPlay) onPlaySound(num, true)
            else onStopSound()
        }/*
        imgBack.setOnClickListener {
            backTrack()
        }*/
        imgNext.setOnClickListener {
            nextTrack()
        }
    }

    private fun backTrack(){
        if (numberTrack == 0)
            onPlaySound(arraySound.size - 1)
        else {
            numberTrack -= 1
            onPlaySound(numberTrack)
        }
    }

    private fun nextTrack(){
        if (numberTrack == arraySound.size - 1)
            onPlaySound(0)
        else {
            numberTrack += 1
            onPlaySound(numberTrack)
        }
    }
}
