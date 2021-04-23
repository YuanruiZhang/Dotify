package edu.uw.yuanrz.dotify

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import kotlin.random.Random

private const val SONG_IMG_ID = "song_image_ID"
private const val SONG_TITLE = "song_title"
private const val SONG_ARTIST = "song_artist"
private const val SONG_OBJ = "song_object"


fun navigateToSongPlayerActivity(context: Context, singleSong: Song) = with(context) {

    // take the song object send it to
    val intent = Intent(this, PlayerActivity::class.java).apply { // declare to launch PlayerActivity
        val bundle = Bundle().apply {
            putInt(SONG_IMG_ID, singleSong.largeImageID)
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
    private var randomNumber = Random.nextInt(1000, 10000)
    private lateinit var uEnteredName: TextView
    private  lateinit var uEditName: EditText
    private lateinit var uChangeBtn: Button
    private lateinit var playTime: TextView
    private lateinit var playBtn: ImageView
    private lateinit var prevBtn: ImageView
    private lateinit var nextBtn: ImageView
    private lateinit var songImg: ImageView
    private lateinit var songTitle: TextView
    private lateinit var songArtist: TextView
    //default false to hide change user name textbox
    private var editUName = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uEnteredName = findViewById(R.id.userName)
        uEditName = findViewById(R.id.editUserName)
        uChangeBtn = findViewById(R.id.changeButton)
        songImg = findViewById(R.id.album)
        songTitle = findViewById(R.id.songTitle)
        songArtist = findViewById(R.id.albumDscr)
        playTime = findViewById(R.id.playTime)
        playBtn = findViewById(R.id.playBtn)
        prevBtn = findViewById(R.id.prevBtn)
        nextBtn = findViewById(R.id.nextBtn)

        val songImgPassed: Int? = intent.extras?.getInt(SONG_IMG_ID)
        val songTitlePassed: String? = intent.extras?.getString(SONG_TITLE)
        //example of how to extract from parcelable extra
        val songPassed: Song? = intent.getParcelableExtra(SONG_OBJ)
        val songArtistPassed: String? = intent.extras?.getString(SONG_ARTIST)

        if (songImgPassed != null && songTitlePassed != null && songArtistPassed != null && songPassed != null) {
            songImg.setImageResource(songImgPassed)
            songTitle.text = songTitlePassed.toString()
            songArtist.text = songPassed.artist
        }



        //looks super redundant unsure how to shrink it
        //write a seperate function. If need a block of comments, break it up.
        uChangeBtn.setOnClickListener{
            onChangeButtonClick()
        }

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
        }

        prevBtn.setOnClickListener {
            prevClicked()
        }

        nextBtn.setOnClickListener {
            nextClicked()
        }
    }

    private  fun onChangeButtonClick(){
        if (uEnteredName.text.toString().equals("")){
            Toast.makeText(this, "Not allowed to edit empty username", Toast.LENGTH_SHORT).show()
        } else {
            editUName = !editUName
            //if user is editing the username
            if (editUName) {
                uEnteredName.visibility = View.GONE
                uEditName.visibility = View.VISIBLE
                //uEditName.editableText.set = uEnteredName.text
                uEditName.setText(uEnteredName.text, TextView.BufferType.EDITABLE)
                uChangeBtn.text = getString(R.string.apply_user)
                // are there ways to modify string xml instead? continue editing.
            } else {
                uEnteredName.text = uEditName.text
                uEnteredName.visibility = View.VISIBLE
                uEditName.visibility = View.GONE
                uChangeBtn.text = getString(R.string.change_user)
            }
        }
    }


    //length is the duration of message
    private fun prevClicked() {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    private fun nextClicked() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }
}