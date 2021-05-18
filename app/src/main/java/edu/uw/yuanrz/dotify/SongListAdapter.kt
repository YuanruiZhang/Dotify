package edu.uw.yuanrz.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
//import com.ericchee.songdataprovider.Song
import edu.uw.yuanrz.dotify.model.Song

import edu.uw.yuanrz.dotify.databinding.ItemSongBinding

class SongListAdapter(private var listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var onSongClickListener: (position: Int, song: Song) -> Unit = { _, _ ->  }

    //auotmically create more view holder whenever user scrolls
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        return SongViewHolder(binding)
    }

    //fit the correct item into view holder
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val singleSong = listOfSongs[position]
        with(holder.binding) {
            //commented out the old way on loading local images. Using coil to fetch online pics
            //ivSongPic.setImageResource(singleSong.smallImageID)
            ivSongPic.load(singleSong.smallImageURL)
            tvSongTitle.text = singleSong.title
            tvSongArtist.text = singleSong.artist

            itemRoot.setOnClickListener {
                onSongClickListener(position, singleSong)
            }
        }
    }

    override fun getItemCount(): Int = listOfSongs.size


    fun updateSongs(newListOfSongs: List<Song>) {
        //using diffutil: A fancier way to add animation when repositing list
        val callback = SongDiffCallback(newListOfSongs, listOfSongs)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)

        this.listOfSongs = newListOfSongs

        //notifyDataSetChanged()
    }

    class SongViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root)


}