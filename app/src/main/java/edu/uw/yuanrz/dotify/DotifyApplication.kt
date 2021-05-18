package edu.uw.yuanrz.dotify

import android.app.Application
import edu.uw.yuanrz.dotify.repository.DataRepository


//val  emailManager: EmailManager by lazy { EmailManager() }

class DotifyApplication:Application() {
    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()

        dataRepository = DataRepository()
    }
}