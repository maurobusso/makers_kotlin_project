package com.example.hangman

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.example.hangman.ui.theme.HangmanTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

    // making the app visible
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//    }
    }

@Composable
fun Flower(modifier: Modifier = Modifier) {
    // Image at the top, centered horizontally
    Image(
        painter = painterResource(id = R.drawable.petals_10),
        contentDescription = "Full petals",
        modifier = modifier
            .height(200.dp)
            .align(Alignment.CenterVertically)
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
fun App(){
    Column (
        modifier = Modifier.fillMaxSize()
        ) {
        // Flower Image
        Flower(modifier = Modifier.padding(top = 16.dp))

        // Traditional XML Layout
        Layout()
    }
}

//     // variables
//     private var attempts = 0 // falseCount
//     private var wordFoundFlag = true // gameOverFlag did they find the word?
//     private lateinit var wordToGuess:String // word to guess
//     private lateinit var visibleWord:String // targetWord visible to the player
//     private lateinit var indexes:MutableList<Int>
//     private var randomNumber=0 // index of the randomly selected word in he words file

//     //function to start the game and reinitialise variables
//     private fun StartGame() {
//         attempts = 0
//         binding.flower.setImageResource(0)
//     }
// }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanTheme {
        App()
    }
}
