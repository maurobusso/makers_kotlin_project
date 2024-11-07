package com.example.hangman

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import com.example.hangman.ui.theme.HangmanTheme

class MainActivity : ComponentActivity() {
    // making the app visible
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_main)
    }


    // variables
    private var attempts = 0 // falseCount
    private var wordFoundFlag = true // gameOverFlag did they find the word?
    private lateinit var wordToGuess:String // word to guess
    private lateinit var visibleWord:String // targetWord visible to the player
    private lateinit var indexes:MutableList<Int>
    private var randomNumber=0 // index of the randomly selected word in he words file

    //function to start the game and reinitialise variables
    private fun StartGame() {
        attempts = 0
        binding.flower.setImageResource(0)

    }


}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanTheme {

    }
}