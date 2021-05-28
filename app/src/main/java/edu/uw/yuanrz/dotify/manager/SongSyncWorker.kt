package edu.uw.yuanrz.dotify.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.uw.yuanrz.dotify.DotifyApplication
import edu.uw.yuanrz.dotify.model.MusicLibrary
import edu.uw.yuanrz.dotify.model.Song
import java.lang.Exception

class SongSyncWorker (
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {
    private val application by lazy { context.applicationContext as DotifyApplication }
    private val songNotificationManager by lazy { application.notificationManager }
    private val DotifyApp: DotifyApplication by lazy { application as DotifyApplication }
    private val dotifyManager: DotifyManager by lazy {application.dotifyManager}
    private val dataRepository by lazy { DotifyApp.dataRepository }

    //push notification
    override suspend fun doWork(): Result {
       return try {
            Log.i("SongSyncWorker", "syncing songs now")
            //fetch a random song to fake as new song, then pass in song notification
            val musics: MusicLibrary = dataRepository.fetchSongs()
            //periodically fetch music library, and update the existing list
            dotifyManager.updateMusics(musics)
            val singleSong: Song = musics.songs.random()
            songNotificationManager.publishNewSongNotification(singleSong)
             Result.success()
        }catch(ex:Exception){
            return Result.failure()
        }
    }
}