package com.example.diarymobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.diarymobileapp.ui.CalendarActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val intent = Intent(this@MainActivity, CalendarActivity::class.java)
            startActivity(intent)
        }
    }
}