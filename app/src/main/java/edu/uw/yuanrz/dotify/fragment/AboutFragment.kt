package edu.uw.yuanrz.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.uw.yuanrz.dotify.BuildConfig
import edu.uw.yuanrz.dotify.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAboutBinding.inflate(inflater)
        val appVersion = "Version: " + BuildConfig.VERSION_NAME.toString()
        with(binding){
            aboutVersion.text = appVersion
        }

        return binding.root
    }

}