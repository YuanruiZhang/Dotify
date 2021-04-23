package edu.uw.yuanrz.dotify

import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song

class SongDiffCallback(private val newSongs: List<Song>, private val oldSongs: List<Song>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldSongs.size

    override fun getNewListSize(): Int = newSongs.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newSingleSong = newSongs[newItemPosition]
        val oldSingleSong = oldSongs[oldItemPosition]

        return newSingleSong.id == oldSingleSong.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newSingleSong = newSongs[newItemPosition]
        val oldSingleSong = oldSongs[oldItemPosition]
        return newSingleSong.id == oldSingleSong.id
    }
}