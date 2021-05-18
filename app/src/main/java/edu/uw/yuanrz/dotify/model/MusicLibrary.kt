package edu.uw.yuanrz.dotify.model

//import com.ericchee.songdataprovider.Song

data class MusicLibrary(
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)

