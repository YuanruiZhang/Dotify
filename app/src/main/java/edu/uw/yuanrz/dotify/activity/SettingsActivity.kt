package edu.uw.yuanrz.dotify.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
//import com.ericchee.songdataprovider.Song
import edu.uw.yuanrz.dotify.model.Song

import edu.uw.yuanrz.dotify.R
import edu.uw.yuanrz.dotify.databinding.ActivitySettingsBinding

private const val SONG_OBJ = "song_object"
private const val PLAY_COUNT_KEY = "play_count"

fun lunchSettingActivity(context: Context, songPassed: Song, playCount: Int) = with(context) {
    startActivity(Intent(this,SettingsActivity::class.java).apply {
        putExtra(SONG_OBJ,songPassed)
        putExtra(PLAY_COUNT_KEY,playCount)
    })
}

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private val navController by lazy {findNavController(R.id.navHost)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater).apply { setContentView(root) }
        with (binding) {
//            val songPassed = intent.extras?.getParcelable<Song>(SONG_OBJ)
//            val playCount = intent.extras?.getInt(PLAY_COUNT_KEY)
//            if (songPassed != null && playCount != null) {
//                Toast.makeText(this@SettingsActivity, playCount.toString(), Toast.LENGTH_SHORT).show()
            //route the bundle from setting acitivity to my start destination fragment
            navController.setGraph(R.navigation.nav_graph,intent.extras)
            setupActionBarWithNavController(navController)
        }
    }

    override fun onNavigateUp() = navController.navigateUp()

}

