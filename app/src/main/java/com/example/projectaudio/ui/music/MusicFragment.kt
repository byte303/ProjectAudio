package com.example.projectaudio.ui.music

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectaudio.adapter.AdapterListMusic
import com.example.projectaudio.helper.Helper
import com.example.projectaudio.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_sound.view.*
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
                view!!.listMusic.adapter =
                    AdapterListMusic(
                        context!!,
                        Helper().getAllAudioFromDevice(context!!)
                    )
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
                    view!!.listMusic.adapter =
                        AdapterListMusic(
                            context!!,
                            Helper().getAllAudioFromDevice(context!!)
                        )
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


}