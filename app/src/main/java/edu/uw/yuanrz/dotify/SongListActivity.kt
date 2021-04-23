package edu.uw.yuanrz.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.ericchee.songdataprovider.Song
//this is a parcelable API. Write to disk and fasten lunch the intent for activity
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.yuanrz.dotify.databinding.ActivitySongListBinding

class SongListActivity : AppCompatActivity() {
    private lateinit var singleSong: Song
    private lateinit var binding:ActivitySongListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //R.layout.activity_song_list
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {
            //get all song objects
            val songs = SongDataProvider.getAllSongs()
             //Set Adapter to Recycler View with data
            val adapter = SongListAdapter(songs)
            rvSong.adapter = adapter

            //when click on a song, set song title and description pair to the bottom rectangle
            adapter.onSongClickListener = { position, singleSong ->
                songAuthorDscr.text = ("${singleSong.title} - ${singleSong.artist}")
                vBottomBox.visibility = View.VISIBLE
                this@SongListActivity.singleSong = singleSong
            }

            vBottomBox.setOnClickListener{
                // We want to launch the Player Actvivity
                navigateToSongPlayerActivity(this@SongListActivity, singleSong)
            }

            btnShuffle.setOnClickListener {
                // On Refresh Click, update the list
                adapter.updateSongs(songs.toMutableList().shuffled())
            }
        }
    }

}