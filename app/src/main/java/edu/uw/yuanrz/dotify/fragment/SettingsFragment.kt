package edu.uw.yuanrz.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.yuanrz.dotify.NavGraphDirections
import edu.uw.yuanrz.dotify.fragment.SettingsFragmentArgs
import edu.uw.yuanrz.dotify.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val navController by lazy {findNavController()}


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



        return binding.root
    }

}