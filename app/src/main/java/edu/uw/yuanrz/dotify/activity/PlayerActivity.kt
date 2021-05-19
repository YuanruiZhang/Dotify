package edu.uw.yuanrz.dotify.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
//import com.ericchee.songdataprovider.Song
import edu.uw.yuanrz.dotify.model.Song
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uw.yuanrz.dotify.DotifyApplication
import edu.uw.yuanrz.dotify.R
import kotlin.random.Random

private const val SONG_IMG_ID = "song_image_ID"
private const val SONG_TITLE = "song_title"
private const val SONG_ARTIST = "song_artist"
private const val SONG_OBJ = "song_object"


fun navigateToSongPlayerActivity(context: Context, singleSong: Song) = with(context) {

    // take the song object send it to
    val intent = Intent(this, PlayerActivity::class.java).apply { // declare to launch PlayerActivity
        val bundle = Bundle().apply {
            putString(SONG_IMG_ID, singleSong.largeImageURL)
            putString(SONG_TITLE, singleSong.title)
            putString(SONG_ARTIST,singleSong.artist)

            // Use parcelable for passing custom objects
            putParcelable(SONG_OBJ, singleSong)
        }
        putExtras(bundle)
    }

    startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {

    companion object {
        val PLAY_COUNT = "playCount"
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save the user's current state
        outState?.run {
            putInt(PLAY_COUNT,randomNumber)
        }
        super.onSaveInstanceState(outState)
    }

    private val DotifyApp: DotifyApplication by lazy {application as DotifyApplication }
    private val dataRepository by lazy { DotifyApp.dataRepository }
    private val dotifyManager by lazy {DotifyApp.dotifyManager }

    private var randomNumber = Random.nextInt(1000, 10000)
//    private lateinit var uEnteredName: TextView
//    private  lateinit var uEditName: EditText
//    private lateinit var uChangeBtn: Button
    private lateinit var playTime: TextView
    private lateinit var playBtn: ImageView
    private lateinit var prevBtn: ImageView
    private lateinit var nextBtn: ImageView
    private lateinit var songImg: ImageView
    private lateinit var songTitle: TextView
    private lateinit var songArtist: TextView
    private lateinit var fabSettingBtn: FloatingActionButton
    private lateinit var songPassed: Song
    //default false to hide change user name textbox
    private var editUName = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Always call the superclass first
        // check whether we're recreating a previously destryoed instance
        if (savedInstanceState != null) {
            with (savedInstanceState) {
                // restore value of members from saved state
                randomNumber = getInt(PLAY_COUNT)
            }
        }
        //else { initialize some stuff}

        setContentView(R.layout.activity_main)

        //uEnteredName = findViewById(R.id.userName)
        //uEditName = findViewById(R.id.editUserName)
        //uChangeBtn = findViewById(R.id.changeButton)
        songImg = findViewById(R.id.album)
        songTitle = findViewById(R.id.songTitle)
        songArtist = findViewById(R.id.albumDscr)
        playTime = findViewById(R.id.playTime)
        playBtn = findViewById(R.id.playBtn)
        prevBtn = findViewById(R.id.prevBtn)
        nextBtn = findViewById(R.id.nextBtn)
        fabSettingBtn = findViewById(R.id.fabSetting)

        val songImgPassed: String? = intent.extras?.getString(SONG_IMG_ID)
        val songTitlePassed: String? = intent.extras?.getString(SONG_TITLE)
        //example of how to extract from parcelable extra
        val maybeSong: Song? = intent.getParcelableExtra(SONG_OBJ)
        if (maybeSong != null){
            songPassed = maybeSong
        }

        val songArtistPassed: String? = intent.extras?.getString(SONG_ARTIST)

        if (songImgPassed != null && songTitlePassed != null && songArtistPassed != null && songPassed != null) {
            songImg.load(songImgPassed)
            songTitle.text = songTitlePassed.toString()
            songArtist.text = songPassed.artist
        }



        //looks super redundant unsure how to shrink it
        //write a seperate function. If need a block of comments, break it up.
//        uChangeBtn.setOnClickListener{
//            onChangeButtonClick()
//        }

        var songChangeColor = false
        songImg.setOnLongClickListener {
            songChangeColor = !songChangeColor
            if(songChangeColor) {
                playTime.setTextColor(Color.RED)
            }
            else{
                playTime.setTextColor(Color.parseColor("#808080"))
            }
            true
        }


        playTime.text = randomNumber.toString()

        playBtn.setOnClickListener {
            randomNumber += 1
            playTime.text = randomNumber.toString()
            dotifyManager.Pause()
            if (dotifyManager.getIfPause()) {
                Toast.makeText(this, "You Paused", Toast.LENGTH_SHORT).show()
            }
        }

        prevBtn.setOnClickListener {
            prevClicked()
        }

        nextBtn.setOnClickListener {
            nextClicked()
        }

        //go to fragment acitivties
        fabSettingBtn.setOnClickListener {
            if (songPassed != null) {
                lunchSettingActivity(this@PlayerActivity,songPassed,randomNumber)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_setting_icon ->
                if (songPassed != null) {
                lunchSettingActivity(this@PlayerActivity,songPassed,randomNumber)
            }
        }
        return super.onOptionsItemSelected(item)
    }


//    private  fun onChangeButtonClick(){
//        if (uEnteredName.text.toString().equals("")){
//            Toast.makeText(this, "Not allowed to edit empty username", Toast.LENGTH_SHORT).show()
//        } else {
//            editUName = !editUName
//            //if user is editing the username
//            if (editUName) {
//                uEnteredName.visibility = View.GONE
//                uEditName.visibility = View.VISIBLE
//                //uEditName.editableText.set = uEnteredName.text
//                uEditName.setText(uEnteredName.text, TextView.BufferType.EDITABLE)
//                uChangeBtn.text = getString(R.string.apply_user)
//                // are there ways to modify string xml instead? continue editing.
//            } else {
//                uEnteredName.text = uEditName.text
//                uEnteredName.visibility = View.VISIBLE
//                uEditName.visibility = View.GONE
//                uChangeBtn.text = getString(R.string.change_user)
//            }
//        }
//    }



    //length is the duration of message
    private fun prevClicked() {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    private fun nextClicked() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }


}