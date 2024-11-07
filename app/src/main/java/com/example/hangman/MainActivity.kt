package com.example.hangman

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.hangman.ui.theme.HangmanTheme
import com.example.hangman_game.Words
//import kotlin.collections.EmptySet.size
import kotlin.random.Random
import com.example.hangman.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    //------------- GLOBAL VARIABLES
    private lateinit var binding: ActivityMainBinding       // connecting components in layout
    private var attempts = 0                                // count - number of times user can guess, starts at 0, ends at 10
    private var gameOverFlag = true                         // boolean - when attempts reach 0, becomes false
    private lateinit var wordToGuess: String                // hidden word user is guessing
    private lateinit var visibleWord: String                // when game is over, hidden word is now visible to user
    private lateinit var indexes: MutableList<Int>          // index representing each letter in word being guessed
    private var randomNumber = 0                            // index of the randomly selected word from Word.kt file


    //-------------- MAIN FUNCTION
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // game begins
        startGame()

        // every letter in alphabet is displayed on a button
        for (letter in 'a'..'z') {
            val buttonId = resources.getIdentifier(letter.toString(), "id", packageName)
            val button = findViewById<View>(buttonId)

            // every time a button is clicked, it is hidden and checks if it is in the word
            button.setOnClickListener {
                indexes = findIndexes(binding, wordToGuess, letter)
                visibleWord = displayLetters(indexes, visibleWord, letter)
                button.visibility=View.GONE
            }
        }
    }


    //--------- GAME BEGINS
    private fun startGame() {

        // displays alphabet buttons and reset game state
        callBackButtons()

        attempts = 0

        // sets initial image
        updateImage(binding, attempts)

        //binding.flower.setImageResource(0)

        randomNumber = Random.nextInt(0, 162)

        wordToGuess = Words.DICTIONARY[randomNumber]

        createBlank(wordToGuess.length, binding)

        visibleWord = binding.word.text.toString()
    }



    //------- MAKES BUTTONS VISIBLE AGAIN -- called in startGame() function
    private fun callBackButtons() {

        for (letter in 'a'..'z') {
            val buttonId = resources.getIdentifier(letter.toString(), "id", packageName)
            val button = findViewById<View>(buttonId)
            button.visibility=View.VISIBLE
        }
    }



    //------- CREATES UNDERSCORES REPRESENTING EACH LETTER IN HIDDEN WORD
    private fun createBlank(size: Int, binding: ActivityMainBinding) {

        binding.word.text = " _ ".repeat(size)
    }



    //------- FINDS INDEXES OF THE LETTERS IN MISSING WORD
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

        // updates attempts & image if letter is not found
        if (indexes.size == 0) {
            if (attempts == 10) {
                gameOverFlag = false
            }
            attempts++
            updateImage(binding, attempts)
        }
        return indexes
    }



    //-------- UPDATES THE HANGMAN -- each attempt, one petal is removed from flower
    private fun updateImage(binding: ActivityMainBinding, attempts: Int){

        // use current attempt count to find correct image
        val imageName = "petals_$attempts"
        val imageResourceId = resources.getIdentifier(imageName, "drawable", packageName)
        //binding.flower.setImageResource(imageResourceId)


        if (imageResourceId != 0) {
            // only set image if resource exists
            binding.flower.setImageResource(imageResourceId)

        } else {
            // default image
            binding.flower.setImageResource(R.drawable.petals_0)
        }
    }



    //------- DISPLAYS LETTER FOR EACH CORRECT GUESS BY USER
    private fun displayLetters(indexes:MutableList<Int>, targetWord:String,letter: Char):String {

        val stringBuilder = StringBuilder(targetWord)

        // reveals correct letter(s)
        if (indexes.size > 0) {
            indexes.map { index ->
                stringBuilder.setCharAt(index * 2, letter.uppercaseChar())
                binding.word.text = stringBuilder.toString()
            }
        }

        // check for win condition
        if (!stringBuilder.contains("_")) {
            gameOverFlag = true
            showGameOverDialog(gameOverFlag)
        }
        return stringBuilder.toString()
    }


    //-------- DISPLAYS MESSAGE TO USER WHETHER THEY WON OR LOST THE GAME
    private fun showGameOverDialog(gameOverFlag: Boolean) {

        val builder = AlertDialog.Builder(this)

        builder.setCancelable(false)

        if (gameOverFlag) {
            builder.setTitle("YOU WON")
            builder.setMessage("Congrats! You Won The Game")

            builder.setPositiveButton("Play Again") { dialog, which ->
                startGame()
            }
            builder.setNegativeButton("Exit") { dialog, which ->
                System.exit(0)
            }

        }

        else {
            builder.setTitle("GAME OVER")
            builder.setMessage("You Lost The Game. The word was ${wordToGuess.uppercase()}")

            builder.setPositiveButton("Play Again") { dialog, which ->
                startGame()
            }
            builder.setNegativeButton("Exit") { dialog, which ->
                System.exit(0)
            }
        }

        //builder.setNegativeButton("Exit") { _, _ -> System.exit(0) }
        builder.show()
    }

}

    @Composable
    fun Flower(modifier: Modifier = Modifier) {
        // Image at the top, centered horizontally
        Image(
            painter = painterResource(id = R.drawable.petals_0),
            contentDescription = "Full petals",
            modifier = modifier
                .height(200.dp)
        )
    }

    @SuppressLint("ServiceCast", "InflateParams")
    @Composable
    fun Layout(modifier: Modifier = Modifier) {
        // Using AndroidView to embed a traditional XML layout within Compose
        AndroidView(
            factory = { context ->
                val layoutInflater = LayoutInflater.from(context)
                layoutInflater.inflate(R.layout.activity_main, null)
            },
            modifier = modifier.fillMaxSize()
        )
    }

    @Composable
    fun App() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Flower Image
                Flower(modifier = Modifier.padding(top = 16.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Traditional XML Layout
                Layout()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        HangmanTheme {
            App()
        }
    }

