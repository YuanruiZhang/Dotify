package edu.uw.yuanrz.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.uw.yuanrz.dotify.databinding.FragmentStatisticsBinding


class StatisticsFragment : Fragment() {
    private val safeArgs: SettingsFragmentArgs by navArgs()
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