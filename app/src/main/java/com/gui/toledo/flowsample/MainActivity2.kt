package com.gui.toledo.flowsample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback

class MainActivity2 : ComponentActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button = findViewById(R.id.button)
        button.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("lifecycle", "onDestroy")
    }

}