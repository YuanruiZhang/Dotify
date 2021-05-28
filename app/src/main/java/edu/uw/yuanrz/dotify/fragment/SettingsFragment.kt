package edu.uw.yuanrz.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.yuanrz.dotify.DotifyApplication
import edu.uw.yuanrz.dotify.NavGraphDirections
import edu.uw.yuanrz.dotify.fragment.SettingsFragmentArgs
import edu.uw.yuanrz.dotify.databinding.FragmentSettingsBinding
import edu.uw.yuanrz.dotify.manager.RefreshSongManager
import edu.uw.yuanrz.dotify.manager.SongNotificationManager

const val NOTIFICATIONS_ENABLED_PREF_KEY = "notifications_enabled"

class SettingsFragment : Fragment() {

    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val navController by lazy {findNavController()}
    private val DotifyApp: DotifyApplication by lazy { context?.applicationContext as DotifyApplication }
    private val refreshSongManager: RefreshSongManager by lazy { DotifyApp.refreshSongManager }

    private val preferences by lazy { DotifyApp.preferences }
    private val songNotificationManager: SongNotificationManager by lazy { DotifyApp.notificationManager }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)

        val passedSong = safeArgs.songObject
        val playCount = safeArgs.playCount
        //Toast.makeText(context, playCount.toString(), Toast.LENGTH_SHORT).show()

        binding.btnProfile.setOnClickListener {
            navController.navigate(NavGraphDirections.actionGlobalProfileFragment())
        }

        binding.btnStats.setOnClickListener{
            navController.navigate(
                NavGraphDirections.actionGlobalStatisticsFragment(
                    passedSong,
                    playCount
                )
            )
        }

        binding.btnAbout.setOnClickListener{
            navController.navigate(NavGraphDirections.actionGlobalAboutFragment())
        }

        binding.switchNotificationsEnabled.isChecked = preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)
        songNotificationManager.isNotificationsEnabled = preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)
        if (preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY,false)){
            refreshSongManager.startRefreshSongsPeriodically()
        }


        binding.switchNotificationsEnabled.setOnCheckedChangeListener { _, isChecked ->
            songNotificationManager.isNotificationsEnabled = isChecked

            if (isChecked){
                refreshSongManager.startRefreshSongsPeriodically()
            }
            // Saving the value in preferences whether the switch is on or not
            preferences.edit {
                putBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, isChecked)
            }

//            if (isChecked) {
//                Toast.makeText(this@SettingsFragment, "Notifications enabled", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this@SettingsFragment, "Notifications are turned off", Toast.LENGTH_SHORT).show()
//            }
        }


        return binding.root
    }

}