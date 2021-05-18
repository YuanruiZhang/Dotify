package edu.uw.yuanrz.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
//import com.ericchee.songdataprovider.Song
import edu.uw.yuanrz.dotify.model.Song

//this is a parcelable API. Write to disk and fasten lunch the intent for activity
import edu.uw.yuanrz.dotify.DotifyApplication
import edu.uw.yuanrz.dotify.SongListAdapter
import edu.uw.yuanrz.dotify.databinding.ActivitySongListBinding
import edu.uw.yuanrz.dotify.model.MusicLibrary
import kotlinx.coroutines.launch

class SongListActivity : AppCompatActivity() {
    val SAVED_SONG ="savedSong"
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save the user's current state
        outState?.run {
            putParcelable(SAVED_SONG,singleSong)
        }
        super.onSaveInstanceState(outState)
    }

    private val DotifyApp: DotifyApplication by lazy {application as DotifyApplication }
    private val dataRepository by lazy { DotifyApp.dataRepository }
    private lateinit var songs: List<Song>

    private lateinit var singleSong: Song
    private lateinit var binding:ActivitySongListBinding
    private lateinit var adapter: SongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //R.layout.activity_song_list
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {
            songs = listOf()
            //get all song objects
            //val songs = SongDataProvider.getAllSongs()
            loadSongList()
             //Set Adapter to Recycler View with data
            adapter = SongListAdapter(songs)
            rvSong.adapter = adapter

            // check whether we're recreating a previously destroyed instance
            if (savedInstanceState != null) {
                with (savedInstanceState) {
                    // restore value of members from saved state
                    var savedSong = getParcelable<Song>(SAVED_SONG)
                    if (savedSong != null) {
                        songAuthorDscr.text = ("${savedSong.title} - ${savedSong.artist}")
                        vBottomBox.visibility = View.VISIBLE
                        this@SongListActivity.singleSong = savedSong
                    }
                }
            }

            //when click on a song, set song title and description pair to the bottom rectangle
            adapter.onSongClickListener = { position, singleSong ->
                songAuthorDscr.text =  ("${singleSong.title} - ${singleSong.artist}")
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

    private fun loadSongList() {
        lifecycleScope.launch {
            runCatching {
                Toast.makeText(this@SongListActivity, "loading...", Toast.LENGTH_SHORT).show()

                val musicLib: MusicLibrary = dataRepository.fetchSongs()
                songs = musicLib.songs
                adapter.updateSongs(songs)

            }.onFailure {
//                binding.rvEmails.visibility = View.GONE
//                binding.tvErroMsg.text = "AN ERROR HAS OCCURRED PLEASE TRY AGAIN"
                Toast.makeText(this@SongListActivity, "Error occurred when fetching your inbox", Toast.LENGTH_SHORT).show()
            }
        }
    }

}