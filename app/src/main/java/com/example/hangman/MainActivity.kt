package com.example.hangman

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.example.hangman.ui.theme.HangmanTheme
import com.example.hangman_game.Words
//import kotlin.collections.EmptySet.size
import kotlin.random.Random
import com.example.hangman.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {
    // variables
    private lateinit var binding: ActivityMainBinding
    private var attempts = 0 // falseCount
    private var wordFoundFlag = true // gameOverFlag did they find the word?
    private lateinit var wordToGuess: String // word to guess
    private lateinit var visibleWord: String // targetWord visible to the player
    private lateinit var indexes: MutableList<Int>
    private var randomNumber = 0 // index of the randomly selected word in he words file

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
        setContentView(binding.root)
        startGame()
        // Get reference to the TextView by its ID
//        val myTextView: TextView = findViewById(R.id.word)
        for (letter in 'a'..'z') {
            val buttonId = resources.getIdentifier(letter.toString(), "id", packageName)
            val button = findViewById<View>(buttonId)

            button.setOnClickListener {

            }
        }
    }

    private fun startGame() {
        attempts = 0
//        binding.flower.setImageResource(0)
        randomNumber = Random.nextInt(0, 162)
        val wordToGuess = Words.DICTIONARY[randomNumber]
        createBlank(wordToGuess.length, binding)
    }

    private fun createBlank(size: Int, binding: ActivityMainBinding) {
        binding.word.text = " _ ".repeat(size)
    }

    private fun findIndexes(
        binding: ActivityMainBinding,
        word: String,
        letter: Char
    ): MutableList<Int> {
        val indexes = mutableListOf<Int>()

        word.mapIndexed { index, char ->
            if (char == letter) {
                indexes.add(index)
            }

        }
        if (indexes.size == 0) {
            if (attempts == 10) {
                wordFoundFlag = false
            }
            attempts++
            updateImage(binding, attempts)
        }
        return indexes
    }
    private fun updateImage(binding: ActivityMainBinding, attempts: Int){
        val imageName = "Hangman_$attempts"
        val imageResourceId = resources.getIdentifier(imageName, "drawable", packageName)
        binding.hangman.SetImageResource(imageResourceId)
    }
}

    @Composable
    fun Flower(modifier: Modifier = Modifier) {
        // Image at the top, centered horizontally
        Image(
            painter = painterResource(id = R.drawable.petals_10),
            contentDescription = "Full petals",
            modifier = modifier
                .height(200.dp)
//                .align(Alignment.CenterHorizontally)
        )
    }

    @SuppressLint("ServiceCast", "InflateParams")
    @Composable
    fun Layout(modifier: Modifier = Modifier) {
        // Using AndroidView to embed a traditional XML layout within Compose
        AndroidView(
            factory = { context ->
                // Here you can define a custom layout if needed
//            val layout = LinearLayout(context)
                val layoutInflater = LayoutInflater.from(context)
                layoutInflater.inflate(R.layout.activity_main, null)
                // Optionally add some views inside this layout

            },
            modifier = modifier.fillMaxSize()
        )
    }

    @Composable
    fun App() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Flower Image
            Flower(modifier = Modifier.padding(top = 16.dp))

            // Traditional XML Layout
            Layout()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        HangmanTheme {
            App()
        }
    }

