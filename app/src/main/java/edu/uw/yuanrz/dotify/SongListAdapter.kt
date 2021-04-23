package edu.uw.yuanrz.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
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
            ivSongPic.setImageResource(singleSong.smallImageID)
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