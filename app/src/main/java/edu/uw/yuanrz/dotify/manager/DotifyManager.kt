package edu.uw.yuanrz.dotify.manager

import android.widget.Toast

class DotifyManager {
    private var ifPause = false

    fun Pause() {
        ifPause = !ifPause
    }

    fun getIfPause(): Boolean {
        return ifPause
    }
}