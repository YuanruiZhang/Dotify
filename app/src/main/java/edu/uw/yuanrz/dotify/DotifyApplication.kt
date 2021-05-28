package edu.uw.yuanrz.dotify

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import edu.uw.yuanrz.dotify.manager.DotifyManager
import edu.uw.yuanrz.dotify.manager.RefreshSongManager
import edu.uw.yuanrz.dotify.manager.SongNotificationManager
import edu.uw.yuanrz.dotify.repository.DataRepository

const val SONG_APP_PREFS_KEY = "Email App Prefs"

class DotifyApplication:Application() {
    lateinit var dataRepository: DataRepository
    lateinit var refreshSongManager: RefreshSongManager
    lateinit var notificationManager: SongNotificationManager
    lateinit var preferences: SharedPreferences


    val  dotifyManager: DotifyManager by lazy { DotifyManager() }


    override fun onCreate() {
        super.onCreate()
        this.refreshSongManager = RefreshSongManager(this)
        this.notificationManager = SongNotificationManager(this)
        this.preferences = getSharedPreferences(SONG_APP_PREFS_KEY, Context.MODE_PRIVATE)

        dataRepository = DataRepository()
    }
}