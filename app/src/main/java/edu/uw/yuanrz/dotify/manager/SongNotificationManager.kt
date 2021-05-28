package edu.uw.yuanrz.dotify.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ericchee.songdataprovider.Song
import edu.uw.yuanrz.dotify.DotifyApplication
import edu.uw.yuanrz.dotify.R
import edu.uw.yuanrz.dotify.activity.*
import kotlin.random.Random

private const val NEW_SONGS_CHANNEL_ID = "New Uploaded Music"
private const val SONG_IMG_ID = "song_image_ID"
private const val SONG_TITLE = "song_title"
private const val SONG_ARTIST = "song_artist"
private const val SONG_OBJ = "song_object"

class SongNotificationManager(
    private val context: Context
) {

    private val DotifyApp: DotifyApplication by lazy {context.applicationContext as DotifyApplication }
    private val notificationManager = NotificationManagerCompat.from(context)
    var isNotificationsEnabled = true

    init {
        // Initialize all channels
        initNotificationChannels()
    }

    fun publishNewSongNotification(singleSong:edu.uw.yuanrz.dotify.model.Song) {
        if (!isNotificationsEnabled) {
            return
        }

        // Define the intent or action you want when user taps on notification
        val intent = Intent(context, PlayerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(SONG_IMG_ID, singleSong.largeImageURL)
            putExtra(SONG_TITLE, singleSong.title)
            putExtra(SONG_ARTIST,singleSong.artist)
            putExtra(SONG_OBJ, singleSong)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT) // dont forget to add PendingIntent.FLAG_UPDATE_CURRENT to send data over


        // Build information you want the notification to show
        val builder = NotificationCompat.Builder(context, NEW_SONGS_CHANNEL_ID)    // channel id from creating the channel
            .setSmallIcon(R.drawable.ic_music)
            .setContentTitle("${singleSong.artist} just released a new song!!!")
            .setContentText("“Listen to ${singleSong.title} now on Dotify”")
            .setContentIntent(pendingIntent)    // sets the action when user clicks on notification
            .setAutoCancel(true)    // This will dismiss the notification tap
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Tell the OS to publish the notification using the info
        with(notificationManager) {
            // notificationId is a unique int for each notification that you must define
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }

    private fun initNotificationChannels() {
        initNewSongsChannel()
        //initPromotionChannel() If there's another channel, go here
    }

    private fun initNewSongsChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Info about the channel
            val name = context.getString(R.string.new_songs)
            val descriptionText = context.getString(R.string.new_songs_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            // Create channel object
            val channel = NotificationChannel(NEW_SONGS_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Tell the Android OS to create a channel
            notificationManager.createNotificationChannel(channel)
        }
    }

//    private fun initPromotionChannel() {
//
//    }

}


