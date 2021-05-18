package edu.uw.yuanrz.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.uw.yuanrz.dotify.DotifyApplication
import edu.uw.yuanrz.dotify.fragment.SettingsFragmentArgs
import edu.uw.yuanrz.dotify.databinding.FragmentStatisticsBinding


class StatisticsFragment : Fragment() {
    private val safeArgs: SettingsFragmentArgs by navArgs()
    //private val DotifyApp: DotifyApplication by lazy {application as DotifyApplication }
    //private val dataRepository by lazy { Dotify }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatisticsBinding.inflate(inflater)
        val passedSong = safeArgs.songObject
        val playCount = safeArgs.playCount
        val statsPlayCountTxt = "Play Count: $playCount"

        with(binding){
            statsAlbum.setImageResource(passedSong.smallImageID)
            statsPlayCount.text = statsPlayCountTxt
        }

        return binding.root
    }

}