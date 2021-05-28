package edu.uw.yuanrz.dotify.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.uw.yuanrz.dotify.DotifyApplication
import edu.uw.yuanrz.dotify.model.MusicLibrary
import edu.uw.yuanrz.dotify.model.Song

class SongSyncWorker (
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {
    private val application by lazy { context.applicationContext as DotifyApplication }
    private val songNotificationManager by lazy { application.notificationManager }
    private val DotifyApp: DotifyApplication by lazy {application as DotifyApplication }
    private val dataRepository by lazy { DotifyApp.dataRepository }

    //push notification
    override suspend fun doWork(): Result {
        //fetch a random song to fake as new song, then pass in song notification
        val singleSong: Song = dataRepository.fetchSongs().songs.random()

        Log.i("SongSyncWorker", "syncing songs now")
        songNotificationManager.publishNewSongNotification(singleSong)

        return Result.success()
    }
}