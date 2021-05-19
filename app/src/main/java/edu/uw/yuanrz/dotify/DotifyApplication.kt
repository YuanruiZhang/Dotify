package edu.uw.yuanrz.dotify

import android.app.Application
import edu.uw.yuanrz.dotify.manager.DotifyManager
import edu.uw.yuanrz.dotify.repository.DataRepository



class DotifyApplication:Application() {
    lateinit var dataRepository: DataRepository
    val  dotifyManager: DotifyManager by lazy { DotifyManager() }


    override fun onCreate() {
        super.onCreate()

        dataRepository = DataRepository()
    }
}