package com.example.projectaudio

class AudioModel {
    private var aPath: String? = null
    private var aName: String? = null
    private var aAlbum: String? = null
    private var aArtist: String? = null

    fun getaPath(): String? {
        return aPath
    }

    fun setaPath(aPath: String?) {
        this.aPath = aPath
    }

    fun getaName(): String? {
        return aName
    }

    fun setaName(aName: String?) {
        this.aName = aName
    }

    fun getaAlbum(): String? {
        return aAlbum
    }

    fun setaAlbum(aAlbum: String?) {
        this.aAlbum = aAlbum
    }

    fun getaArtist(): String? {
        return aArtist
    }

    fun setaArtist(aArtist: String?) {
        this.aArtist = aArtist
    }
}