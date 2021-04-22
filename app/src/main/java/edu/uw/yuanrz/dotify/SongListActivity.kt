package edu.uw.yuanrz.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.yuanrz.dotify.databinding.ActivitySongListBinding

class SongListActivity : AppCompatActivity() {
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

//            // Handle code when clicking item
//            adapter.onPersonClickListener = { position, name ->
//                Toast.makeText(this@MainActivity, "You clicked at pos: $position for $name", Toast.LENGTH_SHORT).show()
//            }
//
//            btnRefresh.setOnClickListener {
//                // On Refresh Click, update the list
//                adapter.updatePeople(people.toMutableList().shuffled())
//            }
        }
    }

}