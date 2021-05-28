package edu.uw.yuanrz.dotify.manager

import android.widget.Toast
import edu.uw.yuanrz.dotify.model.MusicLibrary

class DotifyManager {
    private var ifPause = false
    private var musics: MusicLibrary? = null

    fun Pause() {
        ifPause = !ifPause
    }

    fun getIfPause(): Boolean {
        return ifPause
    }

    fun updateMusics(musics: MusicLibrary){
        this.musics = musics
    }
}