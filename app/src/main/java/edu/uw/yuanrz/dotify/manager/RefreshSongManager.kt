package edu.uw.yuanrz.dotify.manager

import android.content.Context
import androidx.work.*
import androidx.work.impl.model.WorkTypeConverters.NetworkTypeIds.CONNECTED
import java.util.concurrent.TimeUnit

private const val SONG_SYNC_WORK_TAG = "SONG_SYNC_WORK_TAG" // tag to help track notification

class RefreshSongManager(context: Context) {
    private val workManager: WorkManager = WorkManager.getInstance(context)

    //refresh songs after 5 seconds this function is executed
    fun refreshSongs() {

        val request = OneTimeWorkRequestBuilder<SongSyncWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS) // Delay 5 seconds when refreshing
                .setConstraints(
                        Constraints.Builder() //note: it's the work constraints, not constraint layout
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                )
                .addTag(SONG_SYNC_WORK_TAG)
                .build()

        workManager.enqueue(request)
    }

    //refresh song every 15 min only if no notification enqueued
    fun startRefreshSongsPeriodically() {
        if (isSongSyncRunning()) {
            return
        }

        val request = PeriodicWorkRequestBuilder<SongSyncWorker>(20, TimeUnit.MINUTES)
                //make sure using the constraint class instead of constraints layout
                .setConstraints(
                        Constraints.Builder()
                                //.setRequiresCharging(true) //only refresh when user is charging
                                .setRequiredNetworkType(NetworkType.CONNECTED) //only run when network in connected
                                .build()
                )
                .addTag(SONG_SYNC_WORK_TAG)
                .build()

        workManager.enqueue(request)

    }

    // stop pushing notifications
    fun stopPeriodicallyRefreshing() {
        workManager.cancelAllWorkByTag(SONG_SYNC_WORK_TAG)
    }

    private fun isSongSyncRunning(): Boolean {
        //get all tasks under this tag. If any work is running or enqueued, return true
        return workManager.getWorkInfosByTag(SONG_SYNC_WORK_TAG).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }




}