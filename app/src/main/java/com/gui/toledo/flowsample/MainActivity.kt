package com.gui.toledo.flowsample

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var textOne: TextView
    private lateinit var textTwo: TextView
    private val viewModel: MainViewModel by viewModels()
    private val onBackCallback = object : OnBackPressedCallback(enabled = true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textOne = findViewById(R.id.textView)
        textTwo = findViewById(R.id.textView2)
        startFlow()
        lifecycleScope.launch {
            viewModel.generateFlow()
        }
        onBackPressedDispatcher.addCallback(onBackPressedCallback = onBackCallback)
    }

    private fun startFlow() {
        lifecycleScope.launch {
            val thread = Thread.currentThread().name
            viewModel.generateFlow()
                .catch {
                    textOne.text = "occur a exception on ${Thread.currentThread().name}"
                }
                .collect { value ->
                    textOne.text = "collecting $value on: $thread"
                }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val thread = Thread.currentThread().name
            viewModel.generateFlow()
                .catch {
                    lifecycleScope.launch {
                        textTwo.text = "occur a exception on $thread"
                    }
                }
                .collect { value ->
                    lifecycleScope.launch {
                        textTwo.text =
                            "collecting $value on: $thread and set on ${Thread.currentThread().name}"
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackCallback.remove()
        Log.v("lifecycle", "onDestroy")
    }
}